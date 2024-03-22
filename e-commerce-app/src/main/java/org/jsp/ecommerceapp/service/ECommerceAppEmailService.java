package org.jsp.ecommerceapp.service;

import org.jsp.ecommerceapp.model.Merchant;
import org.jsp.ecommerceapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import static org.jsp.ecommerceapp.util.ApplicationConstants.VERIFY_LINK;
import static org.jsp.ecommerceapp.util.ApplicationConstants.USER_VERIFY_LINK;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class ECommerceAppEmailService {
	@Autowired
	private JavaMailSender javaMailSender;

	public String sendWelcomeMail(Merchant merchant, HttpServletRequest request) {
		String siteUrl = request.getRequestURL().toString();
		String url = siteUrl.replace(request.getServletPath(), "");
		String actual_url= url + VERIFY_LINK + merchant.getToken();
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setTo(merchant.getEmail());
			helper.setSubject("Active Your Account");
			helper.setText(actual_url);
			javaMailSender.send(message);
			return "Verification Mail has been sent";
		} catch (Exception e) {
			return "Cannot send verification mail";
		}
	}
	public String sendWelcomeMail(User user, HttpServletRequest request) {
		String siteUrl = request.getRequestURL().toString();
		String url = siteUrl.replace(request.getServletPath(), "");
		String actual_url= url + USER_VERIFY_LINK + user.getToken();
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setTo(user.getEmail());
			helper.setSubject("Active Your Account");
			helper.setText(actual_url);
			javaMailSender.send(message);
			return "Verification Mail has been sent";
		} catch (Exception e) {
			return "Cannot send verification mail";
		}
	}

}
