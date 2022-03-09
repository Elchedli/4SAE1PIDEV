package com.services.Implementations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.detectlanguage.DetectLanguage;
import com.detectlanguage.Result;
import com.detectlanguage.errors.APIError;
import com.entities.Comment;
import com.entities.Post;
import com.entities.Tag;
import com.entities.User;
import com.entities.enums.State;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.CustomsearchRequestInitializer;
import com.google.api.services.customsearch.model.Search;
import com.repositories.CommentRepository;
import com.repositories.PostRepository;
import com.repositories.UserRepository;
import com.services.Interfaces.IPost;

import io.github.ccincharge.newsapi.NewsApi;
import io.github.ccincharge.newsapi.datamodels.Article;
import io.github.ccincharge.newsapi.requests.RequestBuilder;
import io.github.ccincharge.newsapi.responses.ApiArticlesResponse;
import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.data.Word;
import net.sf.extjwnl.dictionary.Dictionary;
@Service
@EnableScheduling

public class PostImpl implements IPost {
	@Autowired
	PostRepository pr;
	@Autowired
	UserRepository ur;
	@Autowired
	CommentRepository cr;
	@Autowired
	EmailSenderService email = new EmailSenderService();

	// CRUUUUUUUUUUD

	@Override
	public String addPost(Post p) {
		String msg="added succesfuly";
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	    Validator validator = factory.getValidator();

	    Set<ConstraintViolation<Post>> constraintViolations = 
	      validator.validate(p);

	    if ((constraintViolations.size() > 0) ){
	    	 msg = "Couldn't add this post: \n";
	      for (ConstraintViolation<Post> contraintes : constraintViolations) {
	            msg = " " + msg
	    		+ "" + contraintes.getRootBeanClass().getSimpleName()+
	           "." + contraintes.getPropertyPath() + " " + contraintes.getMessage() + "\n";
	      }
	   /*   if (voyage.getArrivelDate().before(voyage.getDepartureDate()))
	      {
	            msg = " " + msg + "Voyage.Date Date of ArrivelDate must be after Date of getDepartureDate";
	      }*/

	    } else {
	    	p.setState(State.Open);
	    	pr.save(p);
	 
	    }
		return msg;		
	
	

	}

	@Override
	public void updatePost(Post p) {
		if (pr.findById(p.getId()).isPresent())
			pr.save(p);
		else
			System.out.println("doesnt exist");

	}

	@Override
	public void deletePost(int idp) {
		Post p = pr.getById(idp);
		pr.delete(p);

	}

	@Override
	public List<Post> allPost() {
		return pr.getAllPosts();
	}
	
	@Override
	public void addTag(int idp,String tag){
		String eng=GoogleTTS_Translate.google_Translate("en",tag);
		String result = eng.toLowerCase();
		Post p = pr.getById(idp);
		Tag t=new Tag();
		t.setTag(result);
		if(p.getTag()!=null)
		p.getTag().add(t);
		else
		{
			Set<Tag> set=new HashSet<Tag>();
			set.add(t);
			p.setTag(set);
		}
		pr.save(p);
	}
	//CRUUUUUUUUUUUUUUUUUUD

	@Override
	public List<Post> findTagPosts(Tag t) {
		List<Post> result = new ArrayList<Post>();
		for (Post p : pr.findAll()) {
			if (p.getTag().contains(t))
				result.add(p);
		}

		return result;
	}

	@Override
	public void follow(int idp, int idu) {
		Post p = pr.getById(idp);
		
		if (p.getFollowers() != null && !(p.getFollowers().contains(","+idu+",")))
			{
			p.setFollowers(p.getFollowers().concat(idu+","));
			}
		else
		{
			p.setFollowers(","+idu+",");
		}
		pr.save(p);
	}

	@Override
	public void unfollow(int idp, int idu) {

		Post p = pr.getById(idp);
		if (p.getFollowers() != null)
		p.setFollowers(p.getFollowers().replace(idu+",",""));	
		pr.save(p);

	}

	public List<Integer> stringToList(String s) {
		int i=0;
		List<String> myList = new ArrayList<String>(Arrays.asList(s.split(",")));
		System.out.println("LIST==>"+myList.get(0));
		List<Integer> l=new ArrayList<Integer>();
				for(String string : myList) 
					{
					if(i!=0)
					l.add(Integer.valueOf(string));
				i++;
					}
			
		return l;

	}

	@Override
	public void mailFollowers(Post p, String content) {
	
		// TODO Auto-generated method stub
		User u;
		//if (p.getFollowers() != null) {
			List<Integer> l = stringToList(p.getFollowers());
		
			for (long i : l) {
				u = ur.findById(i).orElse(null);
				System.out.println("id=" + i);
				System.out.println("@=" + u.getEmail());
				email.sendSimpleMessage(u.getEmail(), "Followed Post",
						"Check the update of post: \n" + p.getTitle() + ": \n" + content);
				
			//}
		}

	}

	@Override
	public void validateComment(int idp, int idc) {
		// TODO Auto-generated method stub
		Post p = pr.getById(idp);
		Comment c = cr.getById(idc);
	
		mailFollowers(p, "There is a validated comment :" + c.getContent());
		

	}

	// TREND METHOD*************************************************************
//TOP 3 TRENDS
	public Map<Integer, Post> calculateTendance(int days) throws IOException {

		int trend3 = 0, trend2 = 0, trend1 = 0;

		LocalDateTime today = LocalDateTime.now();
		LocalDateTime dateLimit = today.minusDays(days);
		Map<Integer, Post> Trends = new HashMap<Integer, Post>();
		File f1 = new File("forum/likes.txt"); // Creation of File Descriptor for
											// input file

		String[] words = null; // Intialize the word Array
		// Creation of File Reader object
		List<Post> listePosts = allPost();

		for (Post post : listePosts) {
			System.out.println("ID=" + post.getId());
			FileReader fr = new FileReader(f1);
			BufferedReader br = new BufferedReader(fr); // Creation of
														// BufferedReader object
			String s;

			String idp = String.valueOf(post.getId());
			String like = "commentUp" + idp;
			String comment = "addComment" + idp;

			int count = 0; // Intialize the word to zero
			while ((s = br.readLine()) != null) // Reading Content from the file
			{
				words = s.split(" "); // Split the word using space
				// for (String word : words)
				// {

				LocalDateTime dateAction = LocalDateTime.parse(words[1]);

				if (dateAction.isBefore(today) && dateAction.isAfter(dateLimit))
					if (words[0].equals(like)) // Search for the given word
					{
						count++; // If Present increase the count by one
					} else if (words[0].equals(comment)) // Search for the given
															// word
					{
						count += 2; // If Present increase the count by one
					}

				// Trends.put(comment.getPost().getId(), count)
				// Input word to be searched
			}

			System.out.println("Count=" + count);

			if (count > trend1) {
				trend1 = count;
				if (Trends.get(2) != null)
					Trends.put(3, Trends.get(2));
				else
					Trends.put(2,Trends.get(1));
				if (Trends.get(3) != null)
					Trends.put(2, Trends.get(1));
				Trends.put(1, post);
				System.out.println("into 1");
			} else if (count >= trend2) // Check for count not equal to zero
			{
				trend2 = count;
				if (Trends.get(2) != null)
					Trends.put(3, Trends.get(2));
				Trends.put(2, post);
				System.out.println("into 2");
			} else if (count > trend3) // Check for count not equal to zero
			{
				trend3 = count;
				Trends.put(3, post);
				System.out.println("into 3");
			}
			fr.close();
		}

		return Trends;
	}

	// 0 0 10 ? * 2#1

	public Map<Integer, Post> topPosts(int days) {
		try {
			Map<Integer, Post> trends;

			trends = calculateTendance(days);

			System.out.println("TREND1:" + trends.get(1).getTitle());
			if (trends.get(2) != null)
				System.out.println("TREND2:" + trends.get(2).getTitle());
			if (trends.get(3) != null)
				System.out.println("TREND3:" + trends.get(3).getTitle());
			return trends;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return null;
	}

	@Scheduled(cron = "0 0/30 * * * *")
	public Map<Integer, Post> currentTopPosts() {
		try {
			Map<Integer, Post> trends;

			trends = calculateTendance(1);

			System.out.println("TREND1:" + trends.get(1).getTitle());
			if (trends.get(2) != null)
				System.out.println("TREND2:" + trends.get(2).getTitle());
			if (trends.get(3) != null)
				System.out.println("TREND3:" + trends.get(3).getTitle());
			return trends;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return null;
	}


	
	
	

	//RELATED POSTS *******************************************************************************************
	public List<Integer> relatedposts(int idp) throws IOException, JWNLException {

		Post p = pr.findById(idp).orElse(null);
		Map<Integer, Integer> relatedP = new HashMap<Integer, Integer>();
		for (Post rp : allPost()) {
			if (rp.getId() != p.getId()) {
				int weight = 0;

				int content = 0;
				if (p.getCategory().equals(rp.getCategory())) {

					for (Tag t : rp.getTag()) {
						List<String> relatedT = relatedtags(t.getTag());
						for (String tagR : relatedT) {

							if (p.getTag().contains(tagR))
								weight++;

						}
					}

					content = relatedContent(p.getId(), rp.getId());
					if (weight > 0 || content >= 1) {
						relatedP.put(rp.getId(), 3 * weight + content);
						System.out.println("POST1=" + rp.getId());
						System.out.println("POST2=" + relatedP.get(rp.getId()));
					}
					// }

				}
			}
		}
		if (relatedP != null) {
			final List<Integer> sorted = relatedP.entrySet().stream()
					.sorted(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder())).map(Map.Entry::getKey)
					.collect(Collectors.toList());

			return sorted;
		}

		return null;
		
	}
//END RELATED POSTS *******************************************************************************************
	
	
	
	
	
	public synchronized List<String> getSynonyms(String word, POS pos, boolean firstOnly) throws JWNLException {
		Dictionary wordnet = Dictionary.getDefaultResourceInstance();
		List<String> syns = new ArrayList<>();
		IndexWord indexWord = wordnet.getIndexWord(pos, word);
		if (indexWord == null)
			return syns;
		for (Synset synset : indexWord.getSenses()) {
			for (Word w : synset.getWords()) {
				syns.add(w.getLemma());
			}
			if (firstOnly)
				break;
		}
		return syns.stream().distinct().collect(Collectors.toList());

	}

	public List<String> relatedtags(String t) {
		try {
			Dictionary dictionary = Dictionary.getDefaultResourceInstance();
			IndexWord word;

			word = dictionary.getIndexWord(POS.NOUN, t);

			return getSynonyms(t, POS.NOUN, false);

		} catch (JWNLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String cleanContent(int idp) throws IOException {
		Post p = pr.getById(idp);
		List<String> stopwords = Files.readAllLines(Paths.get("forum/stop_words_english.txt"));

		ArrayList<String> allWords = Stream.of(p.getDescription().toLowerCase().split(" "))
				.collect(Collectors.toCollection(ArrayList<String>::new));
		allWords.removeAll(stopwords);

		return allWords.stream().collect(Collectors.joining(" "));
	}

	public int relatedContent(int idp, int irp) throws IOException, JWNLException {
		Post p = pr.getById(idp);
		Post rp = pr.getById(irp);
		int count = 0;
		String content = cleanContent(irp);
		String title = cleanContent(idp);
		title = GoogleTTS_Translate.google_Translate("en", title);
		content = GoogleTTS_Translate.google_Translate("en", content);
		String[] wordsTitle = title.split(" ");
		String[] wordsContent = content.split(" ");
		for (int j = 0; j < wordsTitle.length; j++) {

			List<String> titleSyn = getSynonyms(wordsTitle[j], POS.NOUN, false);
			for (String word : titleSyn) {
				for (int i = 0; i < wordsContent.length; i++) {

					if (word.equals(wordsContent[i])) {

						count++;
					}

				}
			}
		}
		return count;
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	// GEOO
	// ***************************************************************************
	
	
	
	
	
	// FINAL BOSS
	// **************************************************************************

	@Override
	public Map<Post, Double> advancedSearch(String search) throws Exception {

		String lang;
		if(Character.isUpperCase(search.charAt(0)) || StringUtils.isNumeric(search)  )
			lang="en";
		else
			lang=detectLanguage(search);

		Map<Post, Double> result = new HashMap<Post, Double>();
		Map<String, Double> cities;

		if (search.startsWith("@"))

		{
			String tc = GoogleTTS_Translate.google_Translate("en", search.substring(1));

			List<Post> translated = translatePosts(tagPosts(tc), lang);

			for (Post p : translated) {
				result.put(p, (double) -1);
			}

			return result;

		} else if (isCountry(search)) {

			cities = this.findCities(search);

			return searchPosts(cities, search);

		} else if (isCity(search)) {
			cities = this.findCityRange(30, search);
			System.out.println("CITIES:" + cities);
			return searchPosts(cities);
			// return sortRange();
		} else {

			List<Post> translated = translatePosts(searchPosts(search), lang);

			for (Post p : translated) {
				result.put(p, (double) -1);
			}

			return result;

		}

	}

	// END FINAL
	// BOSS*********************************************************************************
	
	
	
	

	@Override
	public Map<String, Double> findCities(String country) throws IOException {

		FileInputStream file = new FileInputStream(new File("forum/worldcities.xlsx"));
		XSSFWorkbook wb = new XSSFWorkbook(file);
		// Sheet sheet = workbook.getSheetAt(0);
		Map<String, Double> cities = new TreeMap<String, Double>();
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
			XSSFSheet sheet = wb.getSheetAt(sheetIndex);
			for (int rowIndex = 0; rowIndex < sheet.getLastRowNum(); rowIndex++) {
				XSSFRow row = sheet.getRow(rowIndex);
				if (row != null && row.getCell(4).getStringCellValue().toLowerCase().equals(country.toLowerCase())) {

					cities.put(row.getCell(1).getStringCellValue(),
							distance(0, Double.parseDouble(row.getCell(3).getRawValue()), 0,
									Double.parseDouble(row.getCell(4).getRawValue())));
				}
			}
		}

		return cities;

	}

	@Override
	public Map<String, Double> findCities(String country, int index) throws IOException {

		FileInputStream file = new FileInputStream(new File("forum/worldcities.xlsx"));
		XSSFWorkbook wb = new XSSFWorkbook(file);
		// Sheet sheet = workbook.getSheetAt(0);
		Map<String, Double> cities = new TreeMap<String, Double>();
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
			XSSFSheet sheet = wb.getSheetAt(sheetIndex);
			for (int rowIndex = 0; rowIndex < sheet.getLastRowNum(); rowIndex++) {
				XSSFRow row = sheet.getRow(rowIndex);
				XSSFRow row2 = sheet.getRow(index);
				if (row != null && row.getCell(4).getStringCellValue().equals(country)) {

					cities.put(row.getCell(1).getStringCellValue(),
							distance(Double.parseDouble(row2.getCell(3).getRawValue()),
									Double.parseDouble(row2.getCell(4).getRawValue()),
									Double.parseDouble(row.getCell(3).getRawValue()),
									Double.parseDouble(row.getCell(4).getRawValue())));
				}
			}
		}
		return cities;

	}

	private double distance(double lat1, double lon1, double lat2, double lon2) {
		// The math module contains a function
		// named toRadians which converts from
		// degrees to radians.
		lon1 = Math.toRadians(lon1);
		lon2 = Math.toRadians(lon2);
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		// Haversine formula
		double dlon = lon2 - lon1;
		double dlat = lat2 - lat1;
		double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);

		double c = 2 * Math.asin(Math.sqrt(a));

		// Radius of earth in kilometers. Use 3956
		// for miles
		double r = 6371;

		// calculate the result
		return (c * r);
	}

	public Map<String, Double> findCityRange(double range, String city) throws IOException {
		String country = "";
		FileInputStream file = new FileInputStream(new File("forum/worldcities.xlsx"));
		XSSFWorkbook wb = new XSSFWorkbook(file);
		// Sheet sheet = workbook.getSheetAt(0);
		Map<String, Double> cities = new TreeMap<String, Double>();
		int indexx = -1;

		// COUNTRY SEARCH
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
			XSSFSheet sheet = wb.getSheetAt(sheetIndex);
			int rowIndex = 0;
			while (rowIndex < sheet.getLastRowNum()) {
				XSSFRow row = sheet.getRow(rowIndex);
				if (row != null && row.getCell(1).getStringCellValue().toLowerCase().equals(city.toLowerCase())) {

					country = row.getCell(4).getStringCellValue();
					indexx = rowIndex;
					System.out.println("INDEX=" + indexx);
					break;

				} else {
					rowIndex++;
				}
			}

		}
		if (indexx != -1) {
			Map<String, Double> map = findCities(country, indexx);
			Map<String, Double> mapf = new TreeMap<String, Double>();

			for (Entry<String, Double> m : map.entrySet()) {
				if (m.getValue() < range) {
					mapf.put(m.getKey(), m.getValue());
				}
			}

			return mapf;
		}
		return null;

	}

	public List<Post> tagPosts(String t) {
		List<Post> posts = this.allPost();
		List<Post> result = new ArrayList<Post>();
		for (Post p : posts) {
			System.out.println("P=" + p);
			for (Tag tag : p.getTag()) {
				if (tag.getTag().toLowerCase().equals(t.toLowerCase())) {
					result.add(p);
					break;
				}

			}

		}
		return result;

	}

	public boolean isCountry(String s) {
		File file = new File("forum/Countries.txt");

		try {
			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();

				if (line.toLowerCase().contains(s.toLowerCase())) {
					return true;

				}
			}

		} catch (FileNotFoundException e) {
			// handle this
		}
		return false;
	}

	public boolean isCity(String s) throws IOException {
		String country = "";
		FileInputStream file = new FileInputStream(new File("forum/worldcities.xlsx"));
		XSSFWorkbook wb = new XSSFWorkbook(file);
		// Sheet sheet = workbook.getSheetAt(0);
		Map<String, Double> cities = new HashMap<String, Double>();
		int indexx = -1;

		// COUNTRY SEARCH
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
			XSSFSheet sheet = wb.getSheetAt(sheetIndex);
			int rowIndex = 0;
			while (rowIndex < sheet.getLastRowNum()) {
				XSSFRow row = sheet.getRow(rowIndex);
				if (row != null && row.getCell(1).getStringCellValue().equals(s)) {

					country = row.getCell(4).getStringCellValue();
					indexx = rowIndex;
					System.out.println("INDEX=" + indexx);
					break;

				} else {
					rowIndex++;
				}
			}

		}
		if (indexx != -1)
			return true;
		else
			return false;

	}

	public String detectLanguage(String s) throws APIError {
		DetectLanguage.apiKey = "e2e6f0dab459079fe1b1320ce0fcbd4c";

		List<Result> results = DetectLanguage.detect(s);

		Result result = results.get(0);

		return result.language;

	}

	public String TranslateString(int idp, String lang) throws Exception {
		Post p = pr.findById(idp).orElse(null);

		return GoogleTTS_Translate.google_Translate(lang, p.getDescription());

	}

	public Post TranslatePost(int idp, String lang) throws Exception {
		/*
		 * AUTO DETECT LANG
		 * 
		 * DetectLanguage.apiKey ="e2e6f0dab459079fe1b1320ce0fcbd4c";
		 * 
		 * 
		 * 
		 * List<Result> results = DetectLanguage.detect("Hello world"); Result
		 * result = results.get(0); String language=result.language;
		 */

		Post p = pr.findById(idp).orElse(null);
		Post resultP = new Post();
		Set<Comment> lc = new HashSet<Comment>();

		resultP.setDescription(GoogleTTS_Translate.google_Translate(lang, p.getDescription()));
		resultP.setTitle(GoogleTTS_Translate.google_Translate(lang, p.getTitle()));
		for (Comment c : p.getComment()) {
			Comment auxC = c;
			auxC.setContent(GoogleTTS_Translate.google_Translate(lang, p.getDescription()));

			lc.add(auxC);

		}
		resultP.setComment(lc);
		return resultP;
	}

	public List<Post> translatePosts(List<Post> posts, String lang) throws Exception {

		List<Post> result = new ArrayList<Post>();
		if (posts != null)
			for (Post p : posts) {
				result.add(TranslatePost(p.getId(), lang));
			}
		return result;
	}

	public List<Post> translatePosts(Map<Post, Double> posts, String lang) throws Exception {

		List<Post> result = new ArrayList<Post>();
		if (posts != null)
			for (Post p : posts.keySet()) {
				result.add(TranslatePost(p.getId(), lang));
			}
		return result;
	}

	public Map<Post, Double> searchPosts(String s) throws Exception {
		Map<Post, Double> result = new HashMap<Post, Double>();
		// List<Post> sortedP=new ArrayList<Post>();
		Double occ = (double) 0;

		for (Post p : this.allPost()) {
			String clean = this.cleanContent(p.getId());
			String cleanS[] = clean.split(" ");
			int count = 0;
			for (int i = 0; i < cleanS.length; i++) {
				if (s.toLowerCase().equals(cleanS[i].toLowerCase()))
					count++;
			}
if(count>=1)
			result.put(p, occ);
		}

		return result;

	}

	@Override
	public Map<Post, Double> searchPosts(Map<String, Double> cities, String country) throws IOException {

		Map<Post, Double> result = new HashMap<Post, Double>();
		// List<Post> sortedP=new ArrayList<Post>();
		// Double occ = (double) 0;

		for (Post p : this.allPost()) {
			String clean = this.cleanContent(p.getId());
			String cleanS[] = clean.split(" ");
			Double count = (double) 0;
			for (String city : cities.keySet()) {

				for (int i = 0; i < cleanS.length; i++) {

					if (city.toLowerCase().equals(cleanS[i].toLowerCase())) {

						count++;
					}

				}
			}

			for (int j = 0; j < cleanS.length; j++) {
				if (country.toLowerCase().equals(cleanS[j].toLowerCase())) {
					count++;
				}
			}
			if (count >= 1) {
				System.out.println("HOUNII");

				result.put(p, count);
			}

		}

		/*
		 * if (result != null) { Map<Post, Double> sorted =
		 * result.entrySet().stream().sorted(Entry.comparingByValue())
		 * .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) ->
		 * e1, LinkedHashMap::new)); System.out.println("SORTED=" + sorted);
		 * return sorted; }
		 */
		System.out.println("RESULT:" + result.values());
		return result;

	}

	@Override
	public Map<Post, Double> searchPosts(Map<String, Double> cities) throws IOException {

		Map<Post, Double> result = new HashMap<Post, Double>();
		// List<Post> sortedP=new ArrayList<Post>();
		// Double occ = (double) 0;

		for (Post p : this.allPost()) {
			String clean = this.cleanContent(p.getId());
			String cleanS[] = clean.split(" ");
			Double count = (double) 0;
			for (String city : cities.keySet()) {

				for (int i = 0; i < cleanS.length; i++) {

					if (city.toLowerCase().equals(cleanS[i].toLowerCase())) {

						count++;
					}

				}
			}

			if (count >= 1) {
				System.out.println("HOUNII");

				result.put(p, count);
			}

		}

		/*
		 * if (result != null) { Map<Post, Double> sorted =
		 * result.entrySet().stream().sorted(Entry.comparingByValue())
		 * .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) ->
		 * e1, LinkedHashMap::new)); System.out.println("SORTED=" + sorted);
		 * return sorted; }
		 */
		System.out.println("RESULT:" + result.values());
		return result;

	}



	private List<Post> sortOcc(Map<Post, Double> map) {

		List<Post> sortedP = new ArrayList<Post>();

		/*
		 * List<Post> l; if(!map.isEmpty()) l = new ArrayList<Post>
		 * (map.keySet()); else l=null;/
		 * 
		 * 
		 * // Map<Post,Double> sorted=new TreeMap<Post,Double>(map); /*
		 * Map<Post, Double> sorted =
		 * map.entrySet().stream().sorted(comparingByValue()) .collect(toMap(e
		 * -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2,
		 * LinkedHashMap::new));
		 */
		for (Post city : map.keySet()) {

			System.out.println("HERRRRRRRRRE");
			sortedP.add(city);
		}

		// return list;
		return sortedP;

	}

	private List<Post> sortRange() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	
	
	
	
	
	
	
	//NEWS *******************************************************
	
	@Override
	public void news() throws GeneralSecurityException, IOException
	{
	  String searchQuery = "test"; //The query to search
	    String cx = "002845322276752338984:vxqzfa86nqc"; //Your search engine

	    //Instance Customsearch
	    Customsearch cs = new Customsearch.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), null) 
	                   .setApplicationName("My First Project") 
	                   .setGoogleClientRequestInitializer(new CustomsearchRequestInitializer("AIzaSyD14NYrtljPQQKXqZQ-xjOqfr5BFQfuu-0")) 
	                   .build();

	    //Set search parameter
	    Customsearch.Cse.List list = cs.cse().list(searchQuery).setCx(cx); 

	    //Execute search
	    Search result = list.execute();
	    if (result.getItems()!=null){
	        for (com.google.api.services.customsearch.model.Result ri : result.getItems()) {
	            //Get title, link, body etc. from search
	            System.out.println(ri.getTitle() + ", " + ri.getLink());
	        }
	    }
	}
	 @Scheduled(cron = "0 * * * * *") 
	    @Override
	public Map<String, String> breakingNews()
	{
	    	int i=0;
		Map<String,String> news=new HashMap<String,String>();
		NewsApi newsApi = new NewsApi("84a6cac5cb4a482786b450d0f5024102");
		RequestBuilder bitcoinRequest = new RequestBuilder()
			    .setQ("travel")
			    .setLanguage("en");

			ApiArticlesResponse apiArticlesResponse = newsApi.sendTopRequest(bitcoinRequest);

			String responseStatus = apiArticlesResponse.status();
		
			ArrayList<Article> newsArticles = apiArticlesResponse.articles();
			while(i<newsArticles.size() && i<5)
			{
			news.put(newsArticles.get(i).title(),newsArticles.get(i).description());
			i++;
			}
			return news;
		
	}
	    
	    
	    
	    
	    
	    
	}
	
	
	
	

