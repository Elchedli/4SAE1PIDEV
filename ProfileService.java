package com.services.Implementations;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.entities.Profile;
import com.repositories.ProfileRepository;
import com.services.Interfaces.IProfile;

import net.bytebuddy.utility.RandomString;

@Service

public class ProfileService implements IProfile {
	@Autowired
	ProfileRepository pr;

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void ajouterProfile(Profile profile, String siteURL) {
		// Verification
		String randomCode = RandomString.make(64);
		profile.setVerificationCode(randomCode);
		profile.setEnabled(false);

		pr.save(profile);

		sendVerificationEmail(profile, siteURL);
		System.out.println("envoyé");
		/*
		 * 2eme methode
		 * 
		 * String nom=profile.getNom(); String email=profile.getEmail(); try {
		 * emailService.sendNewEmail(nom, email); System.out.println("envoyé"); } catch
		 * (MessagingException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
	}

	@Override
	public List<Profile> retriveAllProfiles() {
		List<Profile> listProfile = (List<Profile>) pr.findAll();

		return listProfile;
	}

	public boolean isEmailUnique(String email) {
		Profile existProfile = pr.findByEmail(email);
		return existProfile == null;
	}

	public Profile getProfileByEmail(String email) {
		return pr.findByEmail(email);
	}

	public boolean verify(String verificationCode) {
		Profile profile = pr.findByVerificationCode(verificationCode);

		if (profile == null || profile.isEnabled()) {
			return false;
		} else {
			pr.enable(profile.getIdProfile());

			return true;
		}
	}

	public boolean Confirm(String deleteCode) {
		Profile profile = pr.findByDeleteCode(deleteCode);

		if (profile == null || profile.isEnabled()) {
			return true;
		} else {
			pr.enable(profile.getIdProfile());

			return false;
		}
	}

	public void deleteProfile(int id, String siteURL) {

		String randomCode = RandomString.make(64);
		try {
			profile.setDeleteCode(randomCode);
			profile.setEnabled(false);

			sendConfirmationEmail(profile, siteURL);
			System.out.println("mail confirmation suppression est envoyé");
			pr.deleteById(id);
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println(randomCode);
			pr.deleteById(id);
		}

	}

	@Override
	public Profile updateProfile(Profile profile) {
		// Profile existProfile = pr.findById(profile.getIdProfile()).get();

		profile.setEnabled(true);
		pr.save(profile);
		return profile;
	}

	@Override
	public Profile updateProfilePhoto(Profile profile) {
		pr.save(profile);
		return profile;
	}

	@Override
	public Profile retriveProfileByName(String nomProfile) {

		return null;
	}

	@Override
	public Profile retriveProfileById(int id) {
		// TODO Auto-generated method stub
		Profile p = pr.findById(id).get();
		return p;
	}

	private Profile profile;

	@Override
	public boolean isEnabled() {
		return profile.isEnabled();
	}

	@Override
	public void sendVerificationEmail(Profile profile, String siteURL) {
		String toAddress = profile.getEmail();
		String fromAddress = "heyy.traveller@gmail.com";
		String senderName = "HeyTraveller";
		String subject = "Please verify your profile";
		String content = "Dear [[name]],<br>" + "Please click the link below to verify your profile:<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" + "Thank you,<br>" + "HeyTraveller.";
		MimeMessage message =  mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper((MimeMessage) message);
	     
	    
	    try {
	    	try {
				helper.setFrom(fromAddress, senderName);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("here .sendVerificationEmail.1");
			}
			helper.setTo(toAddress);
			helper.setSubject(subject);
	    } catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("here .sendVerificationEmail.2");
		}
	    
	    content = content.replace("[[name]]", profile.getNom());
	    String verifyURL = siteURL + "/profile/verify?code=" + profile.getVerificationCode();
	    
	    content = content.replace("[[URL]]", verifyURL);
	     
	    try {
			helper.setText(content, true);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("here .sendVerificationEmail.3");
		}
	     
	    mailSender.send((MimeMessage) message);
	    
	}

	@Override
	public void sendConfirmationEmail(Profile profile, String siteURL) {
		String toAddress = profile.getEmail();
		String fromAddress = "heyy.traveller@gmail.com";
		String senderName = "HeyTraveller";
		String subject = "Please confirm to delete your account";
		String content = "Dear [[name]],<br>" + "Please click the link below to delete your profile:<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">CONFIRM</a></h3>" + "Thank you,<br>" + "HeyTraveller.";
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper((MimeMessage) message);

		try {
			
				try {
					helper.setFrom(fromAddress, senderName);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			helper.setTo(toAddress);
			helper.setSubject(subject);
		} catch (MessagingException e) {

			e.printStackTrace();

		}

		content = content.replace("[[name]]", profile.getNom());
		String verifyURL = siteURL + "/profile/confirm?code=" + profile.getDeleteCode();

		content = content.replace("[[URL]]", verifyURL);

		try {
			helper.setText(content, true);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("here .sendConfirmationEmail.3");
		}

		mailSender.send((MimeMessage) message);

	}

}
