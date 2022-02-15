package boot.spring.po;

import java.io.Serializable;

import org.activiti.engine.task.Task;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("请假表")
public class LeaveApply implements Serializable{
	@ApiModelProperty("主键")
	Integer id;
	
	@ApiModelProperty("流程实例id")
	String process_instance_id;
	
	@ApiModelProperty("用户名")
	String user_id;
	
	@ApiModelProperty("请假起始时间")
	String start_time;
	
	@ApiModelProperty("请假结束时间")
	String end_time;
	
	@ApiModelProperty("请假类型")
	String leave_type;
	
	@ApiModelProperty("请假原因")
	String reason;
	
	@ApiModelProperty("申请时间")
	String apply_time;
	
	@ApiModelProperty("实际请假起始时间")
	String reality_start_time;
	
	@ApiModelProperty("实际请假结束时间")
	String reality_end_time;
	
	@ApiModelProperty("部门经理")
	String deptleader;
	
	Task task;
	
	String executionid;
	
	String activityid;
	
	String state;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProcess_instance_id() {
		return process_instance_id;
	}
	public void setProcess_instance_id(String process_instance_id) {
		this.process_instance_id = process_instance_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getLeave_type() {
		return leave_type;
	}
	public void setLeave_type(String leave_type) {
		this.leave_type = leave_type;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getApply_time() {
		return apply_time;
	}
	public void setApply_time(String apply_time) {
		this.apply_time = apply_time;
	}
	public String getReality_start_time() {
		return reality_start_time;
	}
	public void setReality_start_time(String reality_start_time) {
		this.reality_start_time = reality_start_time;
	}
	public String getReality_end_time() {
		return reality_end_time;
	}
	public void setReality_end_time(String reality_end_time) {
		this.reality_end_time = reality_end_time;
	}
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	public String getDeptleader() {
		return deptleader;
	}
	public void setDeptleader(String deptleader) {
		this.deptleader = deptleader;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getExecutionid() {
		return executionid;
	}
	public void setExecutionid(String executionid) {
		this.executionid = executionid;
	}
	public String getActivityid() {
		return activityid;
	}
	public void setActivityid(String activityid) {
		this.activityid = activityid;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
