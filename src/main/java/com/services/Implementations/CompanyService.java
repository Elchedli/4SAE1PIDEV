package com.services.Implementations;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.entities.Company;
import com.repositories.CompanyRepository;
import com.services.Interfaces.ICompany;

import net.bytebuddy.utility.RandomString;

@Service
public class CompanyService implements ICompany {

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	CompanyRepository cr;

	@Override
	public void ajouterCompany(Company company, String siteURL) {

		// Verification
		if (isEmailUnique(company.getEmail())) {
			String randomCode = RandomString.make(64);
			company.setVerificationCode(randomCode);
			company.setEnabled(false);
			company.setCreatedTime(new Date());
			cr.save(company);
			sendVerificationEmail(company, siteURL);
			System.out.println("Compony profile has been added with success -- Confirmation mail");
			cr.save(company);
		} else {
			System.out.println("This profile already exists ...!");
		}
		/*
		 * String nom=company.getNameCompany(); String email = company.getEmail(); try {
		 * emailService.sendNewEmail(nom, email);
		 * System.out.println("Mail ajout entreprise est envoyé"); } catch
		 * (MessagingException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
	}

	public boolean isEmailUnique(String email) {
		Company existcompany = cr.findByEmail(email);
		return existcompany == null;
	}

	public Company getCompanyByEmail(String email) {
		return cr.findByEmail(email);
	}

	public boolean verify(String verificationCode) {
		Company company = cr.findByVerificationCode(verificationCode);

		if (company == null || company.isEnabled()) {
			return false;
		} else {
			cr.enable(company.getIdCompany());

			return true;
		}
	}

	@Override
	public boolean deleteCompany(Long id) {
		cr.deleteById(id);

		return true;
	}

	public String removeCompany(Long id) {
		cr.deleteById(id);
		String p = "company effacé";
		return p;

	}

	@Override
	public List<Company> retriveAllCompanys() {
		List<Company> listCompanies = (List<Company>) cr.findAll();
		return listCompanies;
	}

	@Override
	public Company updateCompany(Company company) {
		company.setEnabled(true);
		cr.save(company);
		return company;
	}

	@Override
	public Company updateCompanyPhoto(Company company) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Company retriveCompanyById(Long id) {
		Company p = cr.findById(id).get();
		return p;
	}

	@Override
	public Company retriveCompanyByName(String nomCompany) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addPhoto(String title, MultipartFile file) {

		// Company logo = new Company();
		// logo.setLogo(new Binary(file.getBytes()));
		// logo= cr.insert();
		return null; // logo.getIdCompany();
	}

	@Override
	public void sendVerificationEmail(Company company, String siteURL) {
		String toAddress = company.getEmail();
		String fromAddress = "heyy.traveller@gmail.com";
		String senderName = "oussama.zribi@esprit.tn";
		String subject = "Please verify your company";
		String content = "Dear [[name]],<br>" + "Please click the link below to verify your company:<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" + "Thank you,<br>" + "HeyTraveller.";
		MimeMessage message = mailSender.createMimeMessage();
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

		content = content.replace("[[name]]", company.getNameCompany());
		String verifyURL = siteURL + "/company/verify/" + company.getVerificationCode();

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

	private Company company;

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return company.isEnabled();
	}
}
