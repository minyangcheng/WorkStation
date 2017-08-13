package com.min.api.enums;

/**
 * 使用枚举表述常量数据字典
 */
public enum SchoolEnum {

	SUCCESS(10000, "获取学生成功"), NOT_FIND(10001, "未发现该学生"), PARAM_ERROR(10002, "参数错误");

	private int code;

	private String message;

	private SchoolEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public static SchoolEnum stateOf(int index) {
		for (SchoolEnum state : values()) {
			if (state.getCode() == index) {
				return state;
			}
		}
		return null;
	}

}
