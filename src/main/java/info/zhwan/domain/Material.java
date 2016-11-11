package info.zhwan.domain;
import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Material implements Serializable {
	
	private static final long serialVersionUID = 92800526027392603L;
	
	private String materialId;
	private String recipeId;
	private String item1;
	private String item2;
	private String item3;
}
