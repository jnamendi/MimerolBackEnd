package bmbsoft.orderfoodonline.entities;
// Generated Sep 18, 2018 12:33:50 AM by Hibernate Tools 5.2.10.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * ModuleAction generated by hbm2java
 */
@Entity
@Table(name = "module_action")
public class ModuleAction implements java.io.Serializable {

	private Long moduleActionId;
	private String moduleName;
	private String action;
	private String moduleDesc;
	private Set<RoleModule> roleModules = new HashSet<RoleModule>(0);

	public ModuleAction() {
	}

	public ModuleAction(String moduleName, String action, String moduleDesc, Set<RoleModule> roleModules) {
		this.moduleName = moduleName;
		this.action = action;
		this.moduleDesc = moduleDesc;
		this.roleModules = roleModules;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "module_action_id", unique = true, nullable = false)
	public Long getModuleActionId() {
		return this.moduleActionId;
	}

	public void setModuleActionId(Long moduleActionId) {
		this.moduleActionId = moduleActionId;
	}

	@Column(name = "module_name")
	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	@Column(name = "action")
	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Column(name = "module_desc", length = 500)
	public String getModuleDesc() {
		return this.moduleDesc;
	}

	public void setModuleDesc(String moduleDesc) {
		this.moduleDesc = moduleDesc;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "moduleAction")
	public Set<RoleModule> getRoleModules() {
		return this.roleModules;
	}

	public void setRoleModules(Set<RoleModule> roleModules) {
		this.roleModules = roleModules;
	}

}
