package com.dev.utils.logger;

/**
 * detail: 日志工具类
 * @author Ttt
 * ==============
 * 这个类的主要作用是把方法封装好,然后实现传入实体类，或者几个参数,返回处理后的String
 * 不用在代码中，拼接String，然后打印日志，这样代码维护起来方便，也快捷，都是1-2句代码实现具体功能调用
 */
class LogTools {

	/**
	 * 获取分享信息实体类数据
	 * @param sMsgVo 分享实体类对象
	 * @return
	 */
	public static String getShareMsgVoData(TestData.ShareMsgVo sMsgVo) {
		StringBuilder sBuilder = new StringBuilder();
		try {
			if (sMsgVo != null) {
				sBuilder.append("打印分享信息实体类数据");
				sBuilder.append("\n分享标题：" + sMsgVo.sTitle);
				sBuilder.append("\n分享文本：" + sMsgVo.sText);
				sBuilder.append("\n分享的图片路径：" + sMsgVo.sImagePath);
				sBuilder.append("\n标题网络链接：" + sMsgVo.sTitleUrl);
			} else {
				sBuilder.append("sMsgVo 为 null");
			}
		} catch (Exception e) {
		}
		return sBuilder.toString();
	}
	
	/**
	 * 获取用户信息实体类数据
	 * @param uInfoVo 用户信息实体类对象
	 * @return
	 */
	public static String getUserInfoVoData(TestData.UserInfoVo uInfoVo) {
		StringBuilder sBuilder = new StringBuilder();
		try {
			if (uInfoVo != null) {
				sBuilder.append("打印用户信息实体类数据");
				sBuilder.append("\n用户名：" + uInfoVo.uName);
				sBuilder.append("\n用户密码：" + uInfoVo.uPwd);
				sBuilder.append("\n用户年龄：" + uInfoVo.uAge);
			} else {
				sBuilder.append("uInfoVo 为 null");
			}
		} catch (Exception e) {
		}
		return sBuilder.toString();
	}
	
	/**
	 * 获取零散参数数据
	 * @param uName 用户名字
	 * @param sTitle 分享标题
	 * @param uAge 用户年龄
	 * @return
	 */
	public static String getScatteredData(String uName, String sTitle, int uAge) {
		StringBuilder sBuilder = new StringBuilder();
		try {
			sBuilder.append("打印零散参数数据");
			sBuilder.append("\nuName：" + uName);
			sBuilder.append("\nsTitle：" + sTitle);
			sBuilder.append("\nuAge：" + uAge);
		} catch (Exception e) {
		}
		return sBuilder.toString();
	}
}
