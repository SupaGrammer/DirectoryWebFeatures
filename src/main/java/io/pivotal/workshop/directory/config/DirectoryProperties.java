package io.pivotal.workshop.directory.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


//SOLUTION: 
@ConfigurationProperties(prefix = "directory")
//allows us to map the resource files such as properties or YAML files to Java Bean object.
public class DirectoryProperties {

	private String audit = "off";
	private String info = "long";

	public String getAudit() {
		return audit;
	}

	public void setAudit(String audit) {
		this.audit = audit;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
