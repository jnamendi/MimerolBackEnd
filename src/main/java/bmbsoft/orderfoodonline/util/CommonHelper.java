package bmbsoft.orderfoodonline.util;

import bmbsoft.orderfoodonline.entities.CloseOpen;
import bmbsoft.orderfoodonline.model.RestaurantWorkTimeModel;
import bmbsoft.orderfoodonline.model.shared.MenuItemTimeAvailableModel;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommonHelper {
	public static String HasPw(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt(4));
	}

	public static Boolean CheckPw(String password, String hashPassword) {
		return BCrypt.checkpw(password, hashPassword);
	}

	public static String applySha256(String input) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			// Applies sha256 to our input,
			byte[] hash = digest.digest(input.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer(); // This will contain hash as hexidecimal
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String generateToken(String secret) {
		long minutes = System.currentTimeMillis() / 1000 / 60;
		String concat = secret + minutes;
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e);
		}
		byte[] hash = digest.digest(concat.getBytes(Charset.forName("UTF-8")));
		// Decode
		// byte[] asBytes = Base64.getDecoder().decode("c29tZSBzdHJpbmc=");
		// System.out.println(new String(asBytes, "utf-8")); // And the output is: some
		// string
		return Base64.getEncoder().encodeToString(hash);
	}

	public static String toPrettyURL(String string) {
		if (string == null || string.isEmpty())
			return "";
		return Normalizer.normalize(string.trim().toLowerCase(), Form.NFD)
				.replaceAll("\\p{InCombiningDiacriticalMarks}+", "").replaceAll("[^\\p{Alnum}]+", "-");
	}

	public static boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

	public static String formatDecimal(Double number, String langCode, String curCode) {

		Locale locale = new Locale(langCode, langCode.toUpperCase());
		Currency currency = Currency.getInstance(curCode);

		DecimalFormatSymbols df = DecimalFormatSymbols.getInstance(locale);
		df.setCurrency(currency);
		DecimalFormat numberFormat = (DecimalFormat) DecimalFormat.getNumberInstance(locale);
		numberFormat.setCurrency(currency);

		return numberFormat.format(number);
	}

	public static String doUpload(String oldFile, MultipartFile file) throws Exception {
		try {
			if (file == null) {
				if (oldFile != null && !oldFile.isEmpty()) return oldFile;
				else return null;
			}

			// delete file
			if (oldFile != null && !oldFile.isEmpty()) {
				String uploadRootPath = System.getProperty("user.home") + "/";
				delete(new File(uploadRootPath + oldFile));
			}

			String uploadRootPath = System.getProperty("user.home") + "/images";
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			String time = format.format(new Date());

			File uploadRootDir = new File(uploadRootPath + "/" + time);

			if (!uploadRootDir.exists()) {
				uploadRootDir.mkdirs();
			}

			String name = file.getOriginalFilename();

			if (name != null && name.length() > 0) {
				long t = new Date().getTime();
				String fileName = t + "_" + name;
				File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + fileName);

				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(file.getBytes());
				stream.close();

				return "images/" + time + "/" + fileName;
			}
			throw new Exception("File name invalid");
		} catch (Exception e) {
			throw new Exception("Can not upload file!");
		}
	}

	private static void delete(File file) {
		if (file.isDirectory()) {
			for (File deleteMe : Objects.requireNonNull(file.listFiles())) {
				// recursive delete
				delete(deleteMe);
			}
		}
	}

	public static boolean checkBetweenTime(List<RestaurantWorkTimeModel> rwt) {
		try {
			if (rwt == null || rwt.isEmpty())
				return false;
			// check open time close time
			Date now = new Date();
			boolean isOpen = false;
			for (RestaurantWorkTimeModel model : rwt) {
				if(model.getWeekDay().equals(Constant.Weekday.valueOf(now.getDay()).toString())) {
					if (model.getList() != null && !model.getList().isEmpty()) {
						for(CloseOpen co : model.getList()) {
							String openTime = co.getOpenTime();
							Date open = new SimpleDateFormat("HH:mm").parse(openTime);
							Calendar openCalendar = Calendar.getInstance();
							openCalendar.setTime(open);

							String closeTime = co.getCloseTime();
							Date close = new SimpleDateFormat("HH:mm").parse(closeTime);
							Calendar closeCalendar = Calendar.getInstance();
							closeCalendar.setTime(close);

							String someRandomTime = now.getHours() + ":" + now.getMinutes();

							Date d = new SimpleDateFormat("HH:mm").parse(someRandomTime);
							Calendar ca = Calendar.getInstance();
							ca.setTime(d);

							Date x = ca.getTime();
							if (x.after(openCalendar.getTime()) && x.before(closeCalendar.getTime())) {
								isOpen = true;
							}
						}
					}
				}
			}
			return isOpen;

		} catch (Exception e) {
			return false;
		}
	}

	public static boolean compareTime(String openTime, String closeTime) {
		try {
			if (openTime == null || openTime.isEmpty())
				return false;
			if (closeTime == null || closeTime.isEmpty())
				return false;
			// check open time close time
			Date time1 = new SimpleDateFormat("HH:mm").parse(openTime);
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(time1);

			Date time2 = new SimpleDateFormat("HH:mm").parse(closeTime);
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(time2);

			return calendar1.after(calendar2);
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean checkBetweenTimeMenu(List<MenuItemTimeAvailableModel> mitam) {
		try {
			if (mitam == null || mitam.isEmpty())
				return false;
			// check open time close time
			Date now = new Date();
			boolean isOpen = false;
			for (MenuItemTimeAvailableModel model : mitam) {
				if(model.getWeekDay().equals(Constant.Weekday.valueOf(now.getDay()).toString())) {
					if (model.getList() != null && !model.getList().isEmpty()) {
						for(CloseOpen co : model.getList()) {
							String openTime = co.getOpenTime();
							Date open = new SimpleDateFormat("HH:mm").parse(openTime);
							Calendar openCalendar = Calendar.getInstance();
							openCalendar.setTime(open);

							String closeTime = co.getCloseTime();
							Date close = new SimpleDateFormat("HH:mm").parse(closeTime);
							Calendar closeCalendar = Calendar.getInstance();
							closeCalendar.setTime(close);

							String someRandomTime = now.getHours() + ":" + now.getMinutes();

							Date d = new SimpleDateFormat("HH:mm").parse(someRandomTime);
							Calendar ca = Calendar.getInstance();
							ca.setTime(d);

							Date x = ca.getTime();
							if (x.after(openCalendar.getTime()) && x.before(closeCalendar.getTime())) {
								isOpen = true;
							}
						}
					}
				}
			}
			return isOpen;

		} catch (Exception e) {
			return false;
		}
	}

}
