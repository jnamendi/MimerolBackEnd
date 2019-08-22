package bmbsoft.orderfoodonline.util;

import java.util.HashMap;
import java.util.Map;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

public class Constant {
	public static int PageNumber = 20;
	public static int Start = 1;
	public static final int EXPIRATION = 60 * 30;

	public enum PaymentMethod {
		Cash(1), Visa(2), Paypal(3);

		private int value;

		private PaymentMethod(int v) {
			this.value = v;
		}

		private static Map map = new HashMap<>();

		static {
			for (PaymentMethod pageType : PaymentMethod.values()) {
				map.put(pageType.value, pageType);
			}
		}

		public static PaymentMethod valueOf(int pageType) {
			return (PaymentMethod) map.get(pageType);
		}

		public int getValue() {
			return value;
		}
	}

	public enum ContactType {
		Suggestion(1), Complaint(2), Question(3);

		private int value;

		private ContactType(int v) {
			this.value = v;
		}

		private static Map map = new HashMap<>();

		static {
			for (ContactType pageType : ContactType.values()) {
				map.put(pageType.value, pageType);
			}
		}

		public static ContactType valueOf(int pageType) {
			return (ContactType) map.get(pageType);
		}

		public int getValue() {
			return value;
		}
	}

	public enum AccountType {
		Private(1), Company(2), Anonymous(7);

		private int value;

		private AccountType(int v) {
			this.value = v;
		}

		private static Map map = new HashMap<>();

		static {
			for (AccountType pageType : AccountType.values()) {
				map.put(pageType.value, pageType);
			}
		}

		public static AccountType valueOf(int pageType) {
			return (AccountType) map.get(pageType);
		}

		public int getValue() {
			return value;
		}
	}

	public enum EmailType {
		Verify(1), Resend(2), Register(3), ChangePass(4), Payment(5), Delivered(6), Promotion(7), Review(
				8), CreateNewUser(9), NewOrderToOwner(10),OrderCompleted(11);

		private int value;

		private EmailType(int v) {
			this.value = v;
		}

		private static Map map = new HashMap<>();

		static {
			for (EmailType pageType : EmailType.values()) {
				map.put(pageType.value, pageType);
			}
		}

		public static EmailType valueOf(int pageType) {
			return (EmailType) map.get(pageType);
		}

		public int getValue() {
			return value;
		}
	}

	public enum Provider {
		FACEBOOK(1), GOOGLE(2), NORMAL(3), AUTHEN_TOKEN(4);

		private int value;

		private Provider(int v) {
			this.value = v;
		}

		private static Map map = new HashMap<>();

		static {
			for (Provider pageType : Provider.values()) {
				map.put(pageType.value, pageType);
			}
		}

		public static Provider valueOf(int pageType) {
			return (Provider) map.get(pageType);
		}

		public int getValue() {
			return value;
		}
	}

	public enum Order {
		New(1), Inprogress(2), Deliveried(3), Rejected(4), Canceled(5), Done(6), Complete(7);

		private int value;

		private Order(int v) {
			this.value = v;
		}

		private static Map map = new HashMap<>();

		static {
			for (Order pageType : Order.values()) {
				map.put(pageType.value, pageType);
			}
		}

		public static Order valueOf(int pageType) {
			return (Order) map.get(pageType);
		}

		public int getValue() {
			return value;
		}
	}

	public enum Payment {
		PENDING(1), PROCESSING(2), DONE(3);

		private int value;

		private Payment(int v) {
			this.value = v;
		}

		private static Map map = new HashMap<>();

		static {
			for (Payment pageType : Payment.values()) {
				map.put(pageType.value, pageType);
			}
		}

		public static Payment valueOf(int pageType) {
			return (Payment) map.get(pageType);
		}

		public int getValue() {
			return value;
		}
	}

	public enum Status {
		Publish(1), UnPublish(0), Deleted(2), Authorize(3), InAuthorize(4), InActive(5), Used(7);

		private int value;
		private static Map map = new HashMap<>();

		private Status(int value) {
			this.value = value;
		}

		static {
			for (Status pageType : Status.values()) {
				map.put(pageType.value, pageType);
			}
		}

		public static Status valueOf(int pageType) {
			return (Status) map.get(pageType);
		}

		public int getValue() {
			return value;
		}

	}

	public enum ControlType {
		singleChoice(1), multiChoice(2);

		private int value;
		private static Map map = new HashMap<>();

		private ControlType(int value) {
			this.value = value;
		}

		static {
			for (ControlType pageType : ControlType.values()) {
				map.put(pageType.value, pageType);
			}
		}

		public static ControlType valueOf(int pageType) {
			return (ControlType) map.get(pageType);
		}

		public int getValue() {
			return value;
		}

	}

	public static final class ErrorTypeCommon {
		public static final String OK = "OK";
		public static final String NOT_FOUND_ITEM = "NOT_FOUND_ITEM";
		public static final String ERROR_PROCESS_DATA = "ERROR_PROCESS_DATA";
		public static final String INVALID_INPUT = "INVALID_INPUT";
		public static final String EMAIL_EXISTS = "EMAIL_EXISTS";
		public static final String INVALID_EMAIL = "INVALID_EMAIL";
		public static final String REQUEST_IS_NULL = "REQUEST_IS_NULL";
		public static final String FORM_DATA_INVALID = "FORM_DATA_INVALID";
		public static final String REQUIRED_FIELD = "REQUIRED_FIELD";
		public static final String DELETE_MANY = "Error when delete item";
		public static final String EXIST_ITEM = "EXIST_ITEM";
		public static final String Access_Denied = "Access_Denied";
	}

	public static final class ErrorCodeUserApi {
		public static final String ACCOUNT_IS_DELETE = "ACCOUNT_IS_DELETE";
		public static final String PASSWORD_NOT_MATCH = "PASSWORD_NOT_MATCH";
		public static final String ACCOUNT_NOT_ACTIVE = "ACCOUNT_NOT_ACTIVE";
		public static final String OLD_PASS_NOT_MATCH = "OLD_PASS_NOT_MATCH";
		public static final String INVALID_ACCOUNT = "INVALID_ACCOUNT";
		public static final String TOKEN_EXPIRED = "TOKEN_EXPIRED";
		public static final String ACCOUNT_ACTIVE = "ACCOUNT_ACTIVE";

	}

	public static final String ROOT_FODER = "/public/";

	public static final String ROLE_CODE_OWNER = "owner";
	public static final String ROLE_CODE_GUEST = "guest";

	public static class Module {
		public static final String Address = "Address";
		public static final String Category = "Category";
		public static final String ContactUs = "ContactUs";
		public static final String Favourite = "Favourite";
		public static final String Menu = "Menu";
		public static final String MenuItem = "MenuItem";
		public static final String Order = "Order";
		public static final String Promotion = "Promotion";
		public static final String Rating = "Rating";
		public static final String Comment = "Comment";
		public static final String Restaurant = "Restaurant";
		public static final String Role = "Role";
		public static final String User = "User";
		public static final String UserInfo = "UserInfo";

	}

	public static class Action {
		public static final String getAll = "getAll";
		public static final String save = "save";
		public static final String update = "update";
		public static final String insert = "insert";
		public static final String edit = "edit";
		public static final String getByUserId = "getByUserId";
		public static final String getByRestaurantAndUser = "getByRestaurantAndUser";
		public static final String getById = "getById";
		public static final String delete = "delete";
		public static final String getAllByLanguage = "getAllByLanguage";
		public static final String deleteMany = "deleteMany";
		public static final String getByDistrict = "getByDistrict";
		public static final String getAllSortByName = "getAllSortByName";
		public static final String getMenuByRestaurantId = "getMenuByRestaurantId";
		public static final String getByRestaurant = "getByRestaurant";
		public static final String getByOwner = "getByOwner";
		public static final String getFullInfo = "getFullInfo";
		public static final String payment = "payment";
		public static final String getByUser = "getByUser";
		public static final String getOrderPayment = "getOrderPayment";
		public static final String orderRestaurantReview = "orderRestaurantReview";
		public static final String updateOrderBy = "updateOrderBy";
		public static final String getAllByOwner = "getAllByOwner";
		public static final String getMultiCoreById = "getMultiCoreById";
		public static final String getByCode = "getByCode";
		public static final String getBySize = "getBySize";
		public static final String getByName = "getByName";
		public static final String getAllRegisteredCity = "getAllRegisteredCity";
		public static final String getByIdAndlanguageCode = "getByIdAndlanguageCode"; 
		
	}

	public enum Weekday{
		SUN(0), MON(1), TUE(2), WED(3), THU(4), FRI(5), SAT(6);

		private int value;

		Weekday(int v) {
			this.value = v;
		}

		private static Map map = new HashMap<>();

		static {
			for (Weekday day : Weekday.values()) {
				map.put(day.value, day);
			}
		}

		public static Weekday valueOf(int day) {
			return (Weekday) map.get(day);
		}

		public int getValue() {
			return value;
		}

	}

	public static class ContentSmsClient{
		public static final String EN = "The restaurant has confirmed your order %1$s. It will be delivered in about %2$s mins. Enjoy your food.";
		public static final String ES = "El restaurante ha confirmado su orden %s. Se entregará en unos %s minutos. Disfrute su comida.";

	}

	public static class ContentSmsRestaurant{
		public static final String EN = "You have a new order %s. Please go to your admin console to review it and confirm or reject it.";
		public static final String ES = "Tiene una nueva orden %s. Vaya a su consola de administración para revisarla y confirmarla o rechazarla.";
	}
	public static class AmazonKey{
		public static final String ACCESS_KEY = "AKIAVZD3RCWISLZD5VKN";
		public static final String SECRET_KEY = "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";
	}
}
