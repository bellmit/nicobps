package obps.util.application;

public class CommonMap {
    private String key;
    private String value;
    private String value1;
    private String value2;
    private Integer value3;

    public CommonMap() {
    }

    public CommonMap(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public CommonMap(String key, Integer value) {
    	this.key = key;
    	this.value3 = value;
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    public String getValue1() {
        return value1;
    }
    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }
    public void setValue2(String value2) {
        this.value2 = value2;
    }

	public Integer getValue3() {
		return value3;
	}

	public void setValue3(Integer value3) {
		this.value3 = value3;
	}
    
}
