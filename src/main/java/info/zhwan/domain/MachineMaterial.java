package info.zhwan.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class MachineMaterial implements Serializable {
	
	private static final long serialVersionUID = 8960903251389401132L;
	
	private String machineId;
	private String materialId;
	private String startTime;
	private String endTime;
	private String item1;
	private String item2;
	private String item3;
}
