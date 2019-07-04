package bmbsoft.orderfoodonline.service;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Component
public class EmailService {
	@Autowired
	private JavaMailSender mailSender;

	@Async
	public void sendEmail(SimpleMailMessage email) {
		mailSender.send(email);
	}

	public void sendMessage(String from, String to, String subject, String text, String displayName)
			throws MessagingException, IOException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper hepler = new MimeMessageHelper(message, true, "utf-8");
		hepler.setFrom(new InternetAddress(from, displayName));
		hepler.setTo(to);
		hepler.setSubject(subject);
		hepler.setText(text, true);
		mailSender.send(message);
	}

	public void sendCcMessage(String from, String to, String cc, String subject, String text, String displayName)
			throws MessagingException, IOException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper hepler = new MimeMessageHelper(message, true, "utf-8");
		hepler.setFrom(new InternetAddress(from, displayName));
		hepler.setTo(to);
		if (cc != null && cc.isEmpty()) {
			hepler.setCc(cc);
		}
		hepler.setSubject(subject);
		hepler.setText(text, true);
		mailSender.send(message);
	}

	public void sendBccMessage(String from, String to, String bcc, String subject, String text, String displayName)
			throws MessagingException, IOException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper hepler = new MimeMessageHelper(message, true, "utf-8");
		hepler.setFrom(new InternetAddress(from, displayName));
		hepler.setTo(to);
		if (bcc != null && !bcc.isEmpty()) {
			hepler.setBcc(bcc.split(";"));
		}
		hepler.setSubject(subject);
		hepler.setText(text, true);
		mailSender.send(message);
	}

	public void sendMessage(String from, String[] to, String[] cc, String subject, String text, String displayName)
			throws MessagingException, IOException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper hepler = new MimeMessageHelper(message, true, "utf-8");
		hepler.setFrom(new InternetAddress(from, displayName));
		hepler.setTo(to);
		hepler.setCc(cc);
		hepler.setSubject(subject);
		hepler.setText(text, true);
		mailSender.send(message);
	}

	public void sendMessage(String from, String[] to, String subject, String text, String displayName)
			throws MessagingException, IOException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper hepler = new MimeMessageHelper(message, true, "utf-8");
		hepler.setFrom(new InternetAddress(from, displayName));
		hepler.setTo(to);
		hepler.setSubject(subject);
		hepler.setText(text, true);
		mailSender.send(message);
	}
}
