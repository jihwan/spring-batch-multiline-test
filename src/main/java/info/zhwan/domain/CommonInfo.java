package info.zhwan.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class CommonInfo implements Serializable {
	
	private static final long serialVersionUID = -1850247907482415885L;

	public static final String COMMON_INFO = "COMMON_INFO";

	private Header header;
	
	private Material material;
	
	private MachineMaterial machineMaterial;
}
