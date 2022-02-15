package boot.spring.mapper;

import java.util.List;

import boot.spring.po.LeaveApply;

public interface LeaveApplyMapper {
	void save(LeaveApply apply);

	LeaveApply getLeaveApply(int id);

	int updateByPrimaryKey(LeaveApply record);
	
	List<LeaveApply> listLeaveApplyByApplyer(String applyer);
}
