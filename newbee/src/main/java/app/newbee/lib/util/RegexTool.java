/**
 * 
 */
package app.newbee.lib.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RegexTool is used to regex the string ,such as : phone , qq , password ,
 * email .
 * 
 * @version v1.0.1
 * @since JDK5.0
 * 
 */

public class RegexTool {

	/**
	 * 
	 * @param phoneNum
	 *            传入的参数仅仅是一个电话号码时，调用此方法
	 * @return 如果匹配正确，return true , else return else
	 */
	// 如果传进来的是电话号码，则对电话号码进行正则匹配
	public static boolean regexPhoneNumber(String phoneNum) {

		// 电话号码匹配结果
		boolean isPhoneNum_matcher = phoneNum.matches("1[3578]\\d{9}");
		// 如果isPhoneNum_matcher is true , 则return true , else return false
        return isPhoneNum_matcher;
    }
    /**
     * notice:in java \ is \\
     *
     * @Title: checkpostalcode
     * @Description:检查用户名是否合法
     * @param @param postalcode
     * @param @return 设定文件
     * @return boolean 返回类型
     * @throws
     */
    public static boolean checkUserName(String username) {
        boolean ispostalcode_matcher = username.matches("^([a-zA-Z0-9_]){6,20}$");

        return ispostalcode_matcher;
    }
    /**
     * notice:in java \ is \\
     *
     * @Title: checkpostalcode
     * @Description:检查密码是否合法
     * @param @param postalcode
     * @param @return 设定文件
     * @return boolean 返回类型
     * @throws
     */
    public static boolean checkPassword(String psd) {
        boolean ispostalcode_matcher = psd.matches("^[A-Za-z0-9\\^$\\.\\+\\*_@!#%&amp;~=-]{8,32}$");

        return ispostalcode_matcher;
    }
	/**
	 * 邮箱正则验证:"[a-zA-Z_0-9]+@[a-zA-Z0-9]+(\\.[a-zA-Z]{2,}){1,3}" '@'号前没有点'.'
	 * 邮箱正则验证:"^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$" '@'号前有点'.'
	 */

	/**
	 * 
	 * @param email
	 *            传入的参数仅仅是一个邮箱地址时，调用此方法
	 * @return 如果匹配正确，return true , else return false
	 */
	// 如果传进来的是邮箱地址，则对邮箱进行正则匹配
	public static boolean regexEmailAddress(String email) {

		// 邮箱匹配结果
		boolean isEmail_matcher = email.matches("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		// 如果isEmail_matcher value is true , 则 return true , else return false
        return isEmail_matcher;

    }

	/**
	 * 
	 * @param phoneNum
	 *            传入的电话号码
	 * @param email
	 *            传入的邮箱地址
	 * @return 如果匹配正确，return true , else return false
	 */
	public static boolean regexEmailAndPhoneNum(String phoneNum, String email) {

		// 电话号码匹配结果
		boolean isPhoneNum_matcher = phoneNum.matches("1[358]\\d{9}");

        // 邮箱匹配结果
		boolean isEmail_matcher = email.matches("[a-zA-Z_0-9]+@[a-zA-Z0-9]+(\\.[a-zA-Z]{2,}){1,3}");

		// matcher value is true , 则 return true , else return false
        return isEmail_matcher && isPhoneNum_matcher;
    }
    /**
     *
     * @param phoneNum
     *            传入的电话号码
     * @param email
     *            传入的邮箱地址
     * @return 如果匹配正确，return true , else return false
     */
    public static boolean regexEmailOrPhoneNum(String phoneNum, String email) {

        // 电话号码匹配结果
        boolean isPhoneNum_matcher = phoneNum.matches("1[358]\\d{9}");
        // 邮箱匹配结果
        boolean isEmail_matcher = email.matches("[a-zA-Z_0-9]+@[a-zA-Z0-9]+(\\.[a-zA-Z]{2,}){1,3}");

        // matcher value is true , 则 return true , else return false
        return isEmail_matcher || isPhoneNum_matcher;
    }
	/**
	 * 
	 * @param qqNum
	 *            传入的QQ
	 * @return 如果匹配正确，return true， else return false
	 */
	public static boolean regexQQNumber(String qqNum) {

		// QQ号匹配结果
		boolean isQQNum_matcher = qqNum.matches("[1-9]\\d{2,11}");

        return isQQNum_matcher;
    }

	/**
	 * 
	 * @param pwd
	 *            传入的是 密码
	 * @return 如果匹配正确，满足密码规则，return true， else return false
	 */
	public static boolean regexPassWord(String pwd) {

		// 密码匹配结果
		boolean isPassWord_matcher = pwd.matches("[0-9a-zA-Z_@$@]{6,12}");

        return isPassWord_matcher;

    }

	/**
	 * notice:in java \ is \\
	 * 
	 * @Title: checkpostalcode
	 * @Description:检查邮政编码是否合法
	 * @param @param postalcode
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean checkpostalcode(String postalcode) {
		boolean ispostalcode_matcher = postalcode.matches("[1-9]\\d{5}(?!\\d)");

        return ispostalcode_matcher;
    }

	/**
	 * Check a Ic Card is judge
	 * 
	 * @Title: checkICCard
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param cardNumber
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean checkICCard(String cardNumber) {
		boolean iscardNumber_matcher = cardNumber.matches("[1-9]\\d{5}(?!\\d)");
        return iscardNumber_matcher;
    }



	/**
	 * @param
	 * @return 如果是符合网址格式的字符串,返回<b>true</b>,否则为<b>false</b>
	 */
	public static boolean isHomepage(String str) {
		String regex = "http://(([a-zA-z0-9]|-){1,}\\.){1,}[a-zA-z0-9]{1,}-*";
		return match(regex, str);
	}

	/**
	 * @param regex
	 *            正则表达式字符串
	 * @param str
	 *            要匹配的字符串
	 * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
	 */
	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
}
