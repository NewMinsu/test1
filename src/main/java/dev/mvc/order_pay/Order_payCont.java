package dev.mvc.order_pay;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import dev.mvc.cart.CartProcInter;
import dev.mvc.cart.CartVO;
 
@Controller
public class Order_payCont {
//  @Autowired
//  @Qualifier("dev.mvc.member.MemberProc")
//  private MemberProcInter memberProc = null;
  
  @Autowired 
  @Qualifier("dev.mvc.cart.CartProc")
  private CartProcInter cartProc;
  
  public Order_payCont() {
    System.out.println("-> Order_payCont created.");
  }
  
  // http://localhost:9091/order_pay/create.do
  /**
  * 등록 폼
  * @return
  */
  @RequestMapping(value="/order_pay/create.do", method=RequestMethod.GET )
  public ModelAndView create(HttpSession session) {
    ModelAndView mav = new ModelAndView();
    
    int tot = 0;               // 판매 금액 합계 = 판매 금액(단가) * 수량
    int tot_sum = 0;        // 판매 금액 총 합계 = 판매 금액 총 합계 + 판매 금액 합계
    int point_tot = 0;       // 포인트 합계 = 포인트 합계 + (포인트 * 수량)
    int baesong_tot = 0;   // 배송비 합계
    int total_order = 0;     // 전체 주문 금액
    
    int memberno = (int)session.getAttribute("memberno");
    
    // 쇼핑카트에 등록된 상품 목록을 가져옴
    List<CartVO> list = this.cartProc.list_by_memberno(memberno);
    
    for (CartVO cartVO : list) {
      tot = cartVO.getSaleprice() * cartVO.getCnt();  // 판매 금액 합계 = 판매 금액(단가) * 수량
      cartVO.setTot(tot);
      
      // 판매 금액 총 합계 = 판매 금액 총 합계 + 판매 금액 합계
      tot_sum = tot_sum + cartVO.getTot();
      
      // 포인트 합계 = 포인트 합계 + (포인트 * 수량)
      point_tot = point_tot + (cartVO.getPoint() * cartVO.getCnt());
      
    }
    
    if (tot_sum < 30000) { // 상품 주문 금액이 30,000 원 이하이면 배송비 3,000 원 부여
      baesong_tot = 3000;
    }
    
    // 전체 주문 금액 = 판매 금액 총 합계 + 배송비
    total_order = tot_sum + baesong_tot; 
        
    mav.addObject("list", list); // request.setAttribute("list", list);
    
    mav.addObject("tot_sum", tot_sum);     // 판매 금액 총 합계
    mav.addObject("point_tot", point_tot);  // 포인트 합계
    mav.addObject("baesong_tot", baesong_tot);   // 배송비
    mav.addObject("total_order", total_order);  // 전체 주문 금액 
    
    mav.setViewName("/order_pay/create"); // webapp/WEB-INF/views/order_pay/create.jsp
      
    return mav; // forward
  }

}

