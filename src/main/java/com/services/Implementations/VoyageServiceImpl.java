package com.services.Implementations;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormatSymbols;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.entities.Voyage;
import com.entities.enums.Role;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;

import com.itextpdf.text.pdf.PdfWriter;
import com.entities.User;
import com.repositories.IVoyageRepository;
import com.repositories.UserRepository;
import com.services.Interfaces.IVoyageService;

@Service
public class VoyageServiceImpl implements IVoyageService {

	@Autowired
	IVoyageRepository voyageRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public String ajoutVoyage(Voyage voyage) {
		String msg = "";
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		Set<ConstraintViolation<Voyage>> constraintViolations = validator.validate(voyage);

		if ((constraintViolations.size() > 0) | (voyage.getArrivelDate().before(voyage.getDepartureDate()))) {
			msg = "We could not add your trip make sure these are valid : \n";
			for (ConstraintViolation<Voyage> contraintes : constraintViolations) {
				msg = " " + msg + "" + contraintes.getRootBeanClass().getSimpleName() + "."
						+ contraintes.getPropertyPath() + " " + contraintes.getMessage() + "\n";
			}
			if (voyage.getArrivelDate().before(voyage.getDepartureDate())) {
				msg = " " + msg + "Voyage.Date Date of ArrivelDate must be after Date of getDepartureDate";
			}

		} else {
			voyageRepository.save(voyage);
			msg = "Trip added successfully";
		}
		return msg;
	}

	@Override
	public String suppVoyage(int id) {
		if (voyageRepository.findById(id).isPresent()) {
			voyageRepository.delete(voyageRepository.findById(id).get());
			return "The trip is deleted successfully";
		} else
			return "The trip doesn't exist";
	}

	@Override
	public String modifierVoyage(int id, Voyage voyage) {
		String msg = "";
		if (voyageRepository.findById(id).isPresent()) {
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();

			Set<ConstraintViolation<Voyage>> constraintViolations = validator.validate(voyage);

			if ((constraintViolations.size() > 0) | (voyage.getArrivelDate().before(voyage.getDepartureDate()))) {
				// System.out.println("ifff");
				msg = "We could not update your trip make sure these are valid : \n";
				for (ConstraintViolation<Voyage> contraintes : constraintViolations) {
					msg = " " + msg + "" + contraintes.getRootBeanClass().getSimpleName() + "."
							+ contraintes.getPropertyPath() + " " + contraintes.getMessage() + "\n";
				}
				if (voyage.getArrivelDate().before(voyage.getDepartureDate())) {
					msg = " " + msg + "Voyage.Date Date of ArrivelDate must be after Date of getDepartureDate";
				}

			} else {
				voyage.setIdVoyage(id);
				voyageRepository.save(voyage);
				msg = "Trip updated successfully";
			}
			return msg;
		} else
			return "the trip doesn't exist";
	}

	@Override
	public List<Voyage> getAllVoyage() {
		List<Voyage> voyages = (List<Voyage>) voyageRepository.findAll();
		// System.out.println(voyages);
		return voyages;
	}

	@Override
	public List<Voyage> findByDestination(String destination) {
		return voyageRepository.findByDestination(destination);
	}

	@Override
	public List<Voyage> findVoyageFromdate(String month, int year) {
		List<Voyage> voyages = (List<Voyage>) voyageRepository.findAll();
		List<Voyage> voyagesFromMonth = new ArrayList<Voyage>();
		Calendar calendar = Calendar.getInstance();
		Collections.sort(voyages, new Comparator<Voyage>() {
			@Override
			public int compare(Voyage v1, Voyage v2) {
				return v1.getDepartureDate().compareTo(v2.getDepartureDate());
			}
		});
		for (Voyage v : voyages) {
			calendar.setTime(v.getDepartureDate());
			System.out.println(calendar);

			if ((GetNumberFromMonth(month) == calendar.get(Calendar.MONTH)) & (year == calendar.get(Calendar.YEAR))) {
				voyagesFromMonth.add(v);
			}
		}
		return voyagesFromMonth;
	}

	@Override
	@Transactional
	public StringBuffer getpricefromdate(String month, int year, Long idEntreprise) {
		User entreprise = userRepository.findById(idEntreprise).orElse(null);
		List<Voyage> voyages = voyageRepository.FindEntreprise(entreprise);
		StringBuffer texteEN = new StringBuffer("COST OF THE ALL TRIPS OF THE COMPANY : ");
		texteEN.append(entreprise.getUsername());
		texteEN.append("\n");
		texteEN.append("YEAR : ");
		texteEN.append(year);
		texteEN.append(" MONTH : ");
		texteEN.append(month);
		texteEN.append("\n");
		texteEN.append("\n");

		Calendar calendar = Calendar.getInstance();
		float price = 0;
		for (Voyage v : voyages) {
			calendar.setTime(v.getDepartureDate());

			if ((GetNumberFromMonth(month) == calendar.get(Calendar.MONTH)) & (year == calendar.get(Calendar.YEAR))) {
				texteEN.append("TRIP TO ");
				texteEN.append(v.getDestination());
				texteEN.append(" FROM ");
				texteEN.append(v.getDepartureDate());
				texteEN.append(" TO ");
				texteEN.append(v.getArrivelDate());
				texteEN.append(" : ");
				texteEN.append("(Number Of Employee that went to the trip: ");
				texteEN.append(v.getEmployees().size());
				texteEN.append(") ");
				texteEN.append(v.getPrice() * v.getEmployees().size());
				texteEN.append("\n");
				price = price + (v.getPrice() * v.getEmployees().size());
			}

		}
		texteEN.append(" Total Cost :");
		texteEN.append(price);
		return texteEN;
	}

	@Override
	@Transactional
	public StringBuffer getprice(Long idEntreprise) {
		User entreprise = userRepository.findById(idEntreprise).orElse(null);
		List<Voyage> voyages = voyageRepository.FindEntreprise(entreprise);
		StringBuffer texteEN = new StringBuffer("COST OF THE ALL TRIPS OF THE COMPANY : ");
		texteEN.append(entreprise.getUsername());
		texteEN.append("\n");
		texteEN.append("\n");

		float price = 0;
		for (Voyage v : voyages) {
			texteEN.append("TRIP TO ");
			texteEN.append(v.getDestination());
			texteEN.append(" FROM ");
			texteEN.append(v.getDepartureDate());
			texteEN.append(" TO ");
			texteEN.append(v.getArrivelDate());
			texteEN.append(" : ");
			texteEN.append("(Number Of Employee that went to the trip: ");
			texteEN.append(v.getEmployees().size());
			texteEN.append(") ");
			texteEN.append(v.getPrice() * v.getEmployees().size());
			texteEN.append("\n");
			price = price + (v.getPrice() * v.getEmployees().size());

		}
		texteEN.append(" Total Cost :");
		texteEN.append(price);
		return texteEN;
	}

	public int GetNumberFromMonth(String Month) {

		DateFormatSymbols dfsEN = new DateFormatSymbols(Locale.ENGLISH);

		String[] joursSemaineEN = dfsEN.getWeekdays();

		StringBuffer texteEN = new StringBuffer("Jours EN ");

		for (int i = 1; i < joursSemaineEN.length; i++) {
			texteEN.append(" : ");
			texteEN.append(joursSemaineEN[i]);
		}
		/*
		 * texteEN = new StringBuffer("Mois courts EN ");
		 * 
		 * String[] moisCourtsEN = dfsEN.getShortMonths();
		 * 
		 * for (int i = 0; i < moisCourtsEN.length - 1; i++) {
		 * texteEN.append(" : "); texteEN.append(moisCourtsEN[i]); }
		 * 
		 * System.out.println(texteEN);
		 */

		texteEN = new StringBuffer("Mois EN ");
		String[] moisEN = dfsEN.getMonths();

		for (int i = 0; i < moisEN.length - 1; i++) {
			texteEN.append(" : ");
			texteEN.append(moisEN[i]);
			if (moisEN[i].equalsIgnoreCase(Month)) {
				return i;
			}
		}

		return -1;
	}

	@Override
	@Transactional
	public String addEmployeeToVoyage(Long id, int idVoyage) {
		Voyage voyage = voyageRepository.findById(idVoyage).orElse(null);
		User user = userRepository.findById(id).orElse(null);
		List<User> userVoyage = voyage.getEmployees();
		int x = 0;

		for (User u : userVoyage) {
			if (u.getId() == user.getId()) {
				x = x + 1;
			}
		}

		if (x == 0) {
			if (user.getRole() == Role.EMPLOYEE) {
				voyage.getEmployees().add(user);
				return "Employee added successfuly";
			} else
				return "This user is not an employee";
		} else
			return "Employee already has this trip";
	}

	@Override
	@Transactional
	public List<User> GetEmployeeOfVoyage(int idVoyage) {
		Voyage voyage = voyageRepository.findById(idVoyage).orElse(null);
		return voyage.getEmployees();
	}

	@Override
	@Transactional
	public String MatchingUsers(Long idUserOnline, Long idToMatch) {
		User user = userRepository.findById(idUserOnline).orElse(null);
		User userToMatch = userRepository.findById(idToMatch).orElse(null);
		user.getFriends().add(userToMatch);
		List<User> userFriends = user.getFriends();
		List<User> userToMatchFriends = userToMatch.getFriends();
		int x = 0;

		for (User u : userFriends) {
			if (u.getId() == userToMatch.getId()) {
				x = x + 1;
			}
		}
		int y = 0;
		for (User u : userToMatchFriends) {
			if (u.getId() == user.getId()) {
				y = y + 1;
			}

		}

		if (y != 0) {
			return "Matched! Now you can start you Conversation with " + userToMatch.getUsername();
		} else {
			if (x == 0) {
				if (userToMatch.getRole() == Role.EMPLOYEE) {
					user.getFriends().add(userToMatch);
					return "The Request to be matched is sent successfuly";
				} else
					return "This user is not an employee";
			} else
				return "We ALready Sent a request";
		}
	}

	/*
	 * @Override
	 * 
	 * @Transactional public String getEmployeefromvoyage(Long id,int idVoyage)
	 * { Voyage voyage = voyageRepository.findById(idVoyage).orElse(null); User
	 * user = userRepository.findById(id).orElse(null); if
	 * (user.getRole()==Role.EMPLOYEE) { voyage.getEmployees().add(user); return
	 * "Employee added successfuly"; } else return
	 * "Could not add this employee"; }
	 */

	// Matching
	/*
	 * public Map<String, Double> findCities(String country) throws IOException
	 * {
	 * 
	 * FileInputStream file = new FileInputStream(new File("worldcities.xlsx"));
	 * XSSFWorkbook wb = new XSSFWorkbook(file); // Sheet sheet =
	 * workbook.getSheetAt(0); Map<String, Double> cities = new TreeMap<String,
	 * Double>(); for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets();
	 * sheetIndex++) { XSSFSheet sheet = wb.getSheetAt(sheetIndex); for (int
	 * rowIndex = 0; rowIndex < sheet.getLastRowNum(); rowIndex++) { XSSFRow row
	 * = sheet.getRow(rowIndex); if (row != null &&
	 * row.getCell(4).getStringCellValue().toLowerCase().equals(country.
	 * toLowerCase())) {
	 * 
	 * cities.put(row.getCell(1).getStringCellValue(), distance(0,
	 * Double.parseDouble(row.getCell(3).getRawValue()), 0,
	 * Double.parseDouble(row.getCell(4).getRawValue()))); } } }
	 * 
	 * return cities; }
	 */

	@Override
	@Transactional
	public List<Voyage> getVoyageFromProfession(Long id) {
		User user = userRepository.findById(id).orElse(null);
		String profession = user.getProfile().getProfession();
		// String activity = user.getProfile().getNationality();
		List<Voyage> voyageOfUser = user.getVoyagesEmpl();
		List<Voyage> voyages = voyageRepository.findAll();
		List<Voyage> voyagesFromProfession = new ArrayList<Voyage>();

		for (Voyage v : voyages) {
			int x = 0;
			for (Voyage VoUs : voyageOfUser) {
				if (v.getIdVoyage() == VoUs.getIdVoyage()) {
					x = x + 1;
				}
			}

			if (profession.contains(v.getDomain()) & (x != 1)) {
				voyagesFromProfession.add(v);
			}
		}
		return voyagesFromProfession;
	}

	@Override
	// @Transactional
	public List<User> SuggestionUser(Long id) throws IOException {
		User user = userRepository.findById(id).orElse(null);
		String profession = user.getProfile().getProfession();
		List<Voyage> voyages = voyageRepository.findAll();
		List<User> usersToDisplay = new ArrayList<User>();
		// Calendar calendar = Calendar.getInstance();

		for (Voyage v : voyages)
		{
				for (User u : v.getEmployees())
				{	
					if (profession.contains(u.getProfile().getProfession())&(u.getId()!=user.getId()))
					{
						usersToDisplay.add(u);
					}
				}
		}
		/*
		 * for (Voyage v : voyages) { int x=0; for (Voyage VoUs : voyageOfUser)
		 * { if (v.getIdVoyage()==VoUs.getIdVoyage()) { x=x+1; } }
		 * 
		 * if (profession.contains(v.getDomain())&(x!=1)) {
		 * usersToDisplay.add(v); } }
		 */
		return usersToDisplay;
	}

	/*
	 * @Override
	 * 
	 * @Transactional public List<Voyage> getVoyageFromProfession(Long id) {
	 * User user = userRepository.findById(id).orElse(null); String profession =
	 * user.getProfile().getProfession(); // String activity =
	 * user.getProfile().getNationality(); List<Voyage> voyageOfUser =
	 * user.getVoyagesEmpl(); List<Voyage> voyages = voyageRepository.findAll();
	 * List<Voyage> voyagesFromProfession = new ArrayList<Voyage>();
	 * 
	 * 
	 * for (Voyage v : voyages) { int x=0; for (Voyage VoUs : voyageOfUser) { if
	 * (v.getIdVoyage()==VoUs.getIdVoyage()) { x=x+1; } }
	 * 
	 * if (profession.contains(v.getDomain())&(x!=1)) {
	 * voyagesFromProfession.add(v); } } return voyagesFromProfession; }
	 */

	@Override
	public void eventpdf(Long idEntreprise) {
		try {

			User entreprise = userRepository.findById(idEntreprise).orElse(null);
			List<Voyage> voyages = voyageRepository.FindEntreprise(entreprise);
			StringBuffer texteEN = new StringBuffer("COST OF THE ALL TRIPS OF THE COMPANY : ");
			texteEN.append(entreprise.getUsername());
			texteEN.append("\n");
			texteEN.append("\n");

			float price = 0;

			// User user = userRepository.findById(id).orElse(null);
			// List<User> users = (List<User>)userRepository.findAll();
			// List<Event> cities = (List<Event>) eventrepository.findAll();

			String file_name = null;
			// file_name="D:\\Users\\youss\\Desktop\\workspace_spring\\Desktop\\event\\EVENT_"
			// + id + ".pdf";
			file_name = "D:\\Projet\\projett" + idEntreprise + ".pdf";
			Document document = new Document(PageSize.A4, 15, 15, 45, 30);

			PdfWriter.getInstance(document, new FileOutputStream(file_name));

			document.open();

			document.add(new Paragraph(
					"----------------------------------------------------------------------------------------------------------------------------------------"));
			//////////////////////////// pdfDetailFournisseur

			//Font font = FontFactory.getFont("Cooper Black", 15, BaseColor.GRAY);

			Font mainFont = FontFactory.getFont("Cooper Black", 35, BaseColor.BLUE);
			Paragraph para = new Paragraph("Detail Event N° " + entreprise.getId(), mainFont);
			para.setAlignment(Element.ALIGN_CENTER);
			para.setIndentationLeft(10);
			para.setIndentationRight(10);
			para.setSpacingAfter(10);
			document.add(para);
			document.add(new Paragraph(
					"----------------------------------------------------------------------------------------------------------------------------------------"));
			document.add(new Paragraph(" "));
			document.add(new Paragraph(" "));

			document.add(new Paragraph("COST OF THE ALL TRIPS OF THE COMPANY : "));
			document.add(new Paragraph(entreprise.getUsername()));
			document.add(new Paragraph("\n"));
			document.add(new Paragraph("\n"));

			for (Voyage v : voyages) {
				document.add(new Paragraph("TRIP TO "));
				document.add(new Paragraph(v.getDestination()));
				document.add(new Paragraph(" FROM "));
				document.add(new Paragraph(v.getDepartureDate().toString()));
				document.add(new Paragraph(" TO "));
				document.add(new Paragraph(v.getArrivelDate().toString()));
				document.add(new Paragraph(" : "));
				document.add(new Paragraph("(Number Of Employee that went to the trip: "));
				document.add(new Paragraph(v.getEmployees().size()));
				document.add(new Paragraph(") "));
				document.add(new Paragraph(v.getPrice() * v.getEmployees().size()));
				document.add(new Paragraph("\n"));
				price = price + (v.getPrice() * v.getEmployees().size());

			}
			document.add(new Paragraph(" Total Cost :"));
			document.add(new Paragraph(price));

			///////////////////

			/*
			 * PdfPTable table2 = new PdfPTable(4); Font tableHeader =
			 * FontFactory.getFont("Arial", 10, BaseColor.BLACK); Font tableBody
			 * = FontFactory.getFont("Arial", 9, BaseColor.BLACK); PdfPCell name
			 * = new PdfPCell(new Paragraph("Quantite", tableHeader));
			 * name.setBorderColor(BaseColor.BLACK); name.setPaddingLeft(10);
			 * name.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * name.setVerticalAlignment(Element.ALIGN_CENTER);
			 * name.setBackgroundColor(BaseColor.LIGHT_GRAY);
			 * name.setExtraParagraphSpace(5f);
			 */

			/////////////////////////////
			/////////////////////////////
			/////////////////////////////
			////////////////////////////
			/*
			 * for (Event c : cities) { ;
			 * 
			 * 
			 * }
			 */

			/////////////////////////////////
			/*
			 * PdfPCell Id = new PdfPCell(new Paragraph("identifiant Event",
			 * tableHeader)); Id.setBorderColor(BaseColor.BLACK);
			 * Id.setPaddingLeft(10);
			 * Id.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * Id.setVerticalAlignment(Element.ALIGN_CENTER);
			 * Id.setBackgroundColor(BaseColor.LIGHT_GRAY);
			 * Id.setExtraParagraphSpace(5f); table2.addCell(Id);
			 * 
			 * PdfPCell NumParticipant = new PdfPCell(new
			 * Paragraph("Number Participant", tableHeader));
			 * NumParticipant.setBorderColor(BaseColor.BLACK);
			 * NumParticipant.setPaddingLeft(10);
			 * NumParticipant.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * NumParticipant.setVerticalAlignment(Element.ALIGN_CENTER);
			 * NumParticipant.setBackgroundColor(BaseColor.LIGHT_GRAY);
			 * NumParticipant.setExtraParagraphSpace(5f);
			 * table2.addCell(NumParticipant);
			 * 
			 * PdfPCell Location = new PdfPCell(new Paragraph("Location",
			 * tableHeader)); Location.setBorderColor(BaseColor.BLACK);
			 * Location.setPaddingLeft(10);
			 * Location.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * Location.setVerticalAlignment(Element.ALIGN_CENTER);
			 * Location.setBackgroundColor(BaseColor.LIGHT_GRAY);
			 * Location.setExtraParagraphSpace(5f); table2.addCell(Location);
			 * 
			 * PdfPCell Topic = new PdfPCell(new Paragraph("Topic",
			 * tableHeader)); Topic.setBorderColor(BaseColor.BLACK);
			 * Topic.setPaddingLeft(10);
			 * Topic.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * Topic.setVerticalAlignment(Element.ALIGN_CENTER);
			 * Topic.setBackgroundColor(BaseColor.LIGHT_GRAY);
			 * Topic.setExtraParagraphSpace(5f); table2.addCell(Topic);
			 * 
			 * PdfPCell Id1 = new PdfPCell( new
			 * Paragraph(String.valueOf(e.getId()), tableHeader));
			 * Id1.setBorderColor(BaseColor.BLACK); Id1.setPaddingLeft(10);
			 * Id1.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * Id1.setVerticalAlignment(Element.ALIGN_CENTER);
			 * Id1.setBackgroundColor(BaseColor.WHITE);
			 * Id1.setExtraParagraphSpace(5f); table2.addCell(Id1);
			 * 
			 * PdfPCell NumberParticipant = new PdfPCell(new
			 * Paragraph(String.valueOf(e.getNumberParticipant()),
			 * tableHeader)); NumberParticipant.setBorderColor(BaseColor.BLACK);
			 * NumberParticipant.setPaddingLeft(10);
			 * NumberParticipant.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * NumberParticipant.setVerticalAlignment(Element.ALIGN_CENTER);
			 * NumberParticipant.setBackgroundColor(BaseColor.LIGHT_GRAY);
			 * NumberParticipant.setExtraParagraphSpace(5f);
			 * table2.addCell(NumberParticipant);
			 * 
			 * 
			 * PdfPCell location = new PdfPCell(new
			 * Paragraph(String.valueOf(e.getLocation()), tableHeader));
			 * location.setBorderColor(BaseColor.BLACK);
			 * location.setPaddingLeft(10);
			 * location.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * location.setVerticalAlignment(Element.ALIGN_CENTER);
			 * location.setBackgroundColor(BaseColor.LIGHT_GRAY);
			 * location.setExtraParagraphSpace(5f); table2.addCell(location);
			 * 
			 * 
			 * PdfPCell Topic1 = new PdfPCell(new
			 * Paragraph(String.valueOf(e.getTopic()), tableHeader));
			 * Topic1.setBorderColor(BaseColor.BLACK);
			 * Topic1.setPaddingLeft(10);
			 * Topic1.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * Topic1.setVerticalAlignment(Element.ALIGN_CENTER);
			 * Topic1.setBackgroundColor(BaseColor.LIGHT_GRAY);
			 * Topic1.setExtraParagraphSpace(5f); table2.addCell(Topic1);
			 * 
			 */

			// -----------------------------

			document.add(new Paragraph("  "));

			// document.add(table2);
			document.add(new Paragraph("  "));
			document.add(new Paragraph("  "));

			document.add(new Paragraph(""));
			// "----------------------------------------------------------------------------------------------------------------------------------------"));
			document.add(new Paragraph("  "));
			document.add(new Paragraph("  "));

			document.add(new Paragraph("Phone  :(+216) 72 000 000   "
					+ "                                                             Adresse : Ariana "));
			document.add(new Paragraph("Fax          :(+216) 72 000 000   "
					+ "                                                                               Postal code :4000  "));
			document.add(new Paragraph("Email       :Admin@HeyTraveler.com  "));

			document.close();
		} catch (FileNotFoundException | DocumentException f) {
			// TODO Auto-generated catch block
		}
	}

}
