package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebFilter(value = {"/board/*", "/comment/*", "/member/logout"})
public class LoginFilter extends HttpFilter implements Filter {
       
	private static final long serialVersionUID = 1L;

	public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpSession session = ((HttpServletRequest)req).getSession();
		
		Object login = session.getAttribute("login");
		
		boolean isLogin = false;
		if( login != null) {
			isLogin = (boolean) login;
		}
		
		if( isLogin ) {
			chain.doFilter(req, response);
		} else {
			((HttpServletRequest)req).getRequestDispatcher("/WEB-INF/views/member/loginError.jsp").forward(req, response);
		}
		
	}

}
