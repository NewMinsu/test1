package dev.mvc.cart;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CartCont {
  @Autowired 
  @Qualifier("dev.mvc.cart.CartProc")
  private CartProcInter cartProc;
  
  public CartCont() {
    System.out.println("-> CartCont created.");
  }
  
  // http://localhost:9091/cart/create.do
  /**
   * Ajax 등록 처리
   * INSERT INTO cart(cartno, contentsno, memberno, cnt, rdate)
   * VALUES(cart_seq.nextval, #{contentsno}, #{memberno}, #{cnt}, sysdate)
   * @param categrpVO
   * @return
   */
  @RequestMapping(value="/cart/create.do", method=RequestMethod.POST )
  @ResponseBody
  public String create(HttpSession session,
                            int contentsno) {
    CartVO cartVO = new CartVO();
    cartVO.setContentsno(contentsno);  // 상품 번호
    
    int memberno = (Integer)session.getAttribute("memberno");
    cartVO.setMemberno(memberno);   // 회원 번호
    
    cartVO.setCnt(1); // 최초 구매 수량 1개로 지정
    
    int cnt = this.cartProc.create(cartVO); // 등록 처리
    
    JSONObject json = new JSONObject();
    json.put("cnt", cnt); // 1: 정상 등록
    
    // System.out.println("-> cartCont create: " + json.toString());

    return json.toString();
  }
  
  /**
   * 회원별 목록
   * 할인 금액 합계 = 할인 금액 * 수량
   * 할인 금액 총 합계 = 할인 금액 총 합계 + 할인 금액 합계
   * 포인트 합계 = 포인트 합계 + (포인트 * 수량)
   * 배송비 = 3000
   * 전체 주문 금액 = 할인 금액 총 합계 + 배송비
   * http://localhost:9091/cart/list_by_memberno.do
   * http://localhost:9091/cart/list_by_memberno.do?cateno=
   * http://localhost:9091/cart/list_by_memberno.do?cateno=4
   * @return
   */
  @RequestMapping(value="/cart/list_by_memberno.do", method=RequestMethod.GET )
  public ModelAndView list_by_memberno(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    
    int tot = 0;               // 판매 금액 합계 = 판매 금액(단가) * 수량
    int tot_sum = 0;        // 판매 금액 총 합계 = 판매 금액 총 합계 + 판매 금액 합계
    int point_tot = 0;       // 포인트 합계 = 포인트 합계 + (포인트 * 수량)
    int baesong_tot = 0;   // 배송비 합계
    int total_order = 0;    // 전체 주문 금액
    
    if (session.getAttribute("memberno") != null) { // 회원으로 로그인을 했다면 쇼핑카트로 이동
      int memberno = (int)session.getAttribute("memberno");
      
      // 목록
      ArrayList<CartVO> list = this.cartProc.list_by_memberno(memberno);
      
      for (CartVO cartVO : list) {
        tot = cartVO.getSaleprice() * cartVO.getCnt();  // 판매 금액 합계 = 판매 금액(단가) * 수량
        cartVO.setTot(tot);
        
        // 판매 금액 총 합계 = 판매 금액 총 합계 + 판매 금액 합계
        tot_sum = tot_sum + cartVO.getTot();
        
        // 포인트 합계 = 포인트 합계 + (포인트 * 수량)
        point_tot = point_tot + (cartVO.getPoint() * cartVO.getCnt());
        
      }
      
      if (tot_sum < 30000) { // 상품 주문 금액이 30,000 원 이하이면 배송비 3,000 원 부여
        if (list.size() > 0) {     // 총 주문 금액이 30,000 이하이면서 상품이 존재한다면 배송비 3,000 할당
          baesong_tot = 3000;
        }
      }
      
      // 전체 주문 금액 = 판매 금액 총 합계 + 배송비
      total_order = tot_sum + baesong_tot; 
          
      mav.addObject("list", list); // request.setAttribute("list", list);
      
      mav.addObject("tot_sum", tot_sum);     // 판매 금액 총 합계
      mav.addObject("point_tot", point_tot);  // 포인트 합계
      mav.addObject("baesong_tot", baesong_tot);   // 배송비
      mav.addObject("total_order", total_order);  // 전체 주문 금액 
      
      mav.setViewName("/cart/list_by_memberno"); // /WEB-INF/views/categrp/list_by_memberno.jsp
      
    } else { // 회원으로 로그인하지 않았다면
      // http://localhost:9091/member/login.do?return_url=/cart/list_by_memberno.do
      
      mav.addObject("return_url", "/cart/list_by_memberno.do"); // 로그인 후 이동할 주소 ★
      
      mav.setViewName("redirect:/member/login.do"); // /WEB-INF/views/member/login_ck_form.jsp

    }
    return mav;
  }
  
  /**
   * 수량 변경, http://localhost:9091/cart/delete.do
   * @param session
   * @param cartno 쇼핑카트 번호
   * @param cnt 수량
   * @return 변경된 레코드 갯수
   */
  @RequestMapping(value="/cart/update_cnt.do", method=RequestMethod.POST )
  public ModelAndView update_cnt(HttpSession session, CartVO cartVO) {
    ModelAndView mav = new ModelAndView();

    this.cartProc.update_cnt(cartVO);      
    mav.setViewName("redirect:/cart/list_by_memberno.do");
    
    return mav;
  }
  
  /**
   * 상품 삭제
   * http://localhost:9091/cart/delete.do
   * @return
   */
  @RequestMapping(value="/cart/delete.do", method=RequestMethod.POST )
  public ModelAndView delete(HttpSession session, @RequestParam(value="cartno", defaultValue="0") int cartno ) {
    ModelAndView mav = new ModelAndView();
    
    this.cartProc.delete(cartno);      
    mav.setViewName("redirect:/cart/list_by_memberno.do");
    
    return mav;
  }
  
}

