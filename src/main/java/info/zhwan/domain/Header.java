package info.zhwan.domain;

import java.io.Serializable;

import org.springframework.util.Assert;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Header implements Serializable {
	
	private static final long serialVersionUID = -4681595446547285041L;

	private String machineType;
	private String machineId;
	private String fileCreatedTime;
	
	public void setMachineId(String machineId) {
		Assert.hasText(machineId, "MACHINE_ID must not be null");
		this.machineId = machineId;
	}
}
