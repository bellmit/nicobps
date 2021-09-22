package obps.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import obps.daos.DaoInitializationInterface;
import obps.domains.DomainUserManagementInterface;
import obps.models.FeeMaster;
import obps.models.FeeTypes;
import obps.models.LicenseesRegistrationsm;
import obps.models.Occupancies;
import obps.models.Offices;
import obps.models.Questionnaire;
import obps.models.SubOccupancies;
import obps.models.Usages;
import obps.util.application.ServiceUtilInterface;

@Service("serviceInitialization")
public class ServiceInitialization implements ServiceInitializationInterface {
	@Autowired
	private ServiceUtilInterface serviceUtilInterface;
	
	@Autowired
	private DaoInitializationInterface DaoInitializationInterface;
	
	@Autowired
	private DomainUserManagementInterface DomainUserManagementInterface;

	@Override
	public Long getMaxLicenseecode() {
		String sql = "SELECT MAX(licenseeregistrationcode) FROM masters.licenseesregistrationsm ";
		return serviceUtilInterface.getMaxValue(sql) + 1;
	}

	@Override
	public Long getMaxFeeTypecode() {
		String sql = "SELECT MAX(feetypecode) FROM masters.feetypes ";
		return serviceUtilInterface.getMaxValue(sql) + 1;
	}

	@Override
	public Long getMaxFeeCode() {
		String sql = "SELECT MAX(feecode) FROM masters.feemaster ";
		return serviceUtilInterface.getMaxValue(sql) + 1;
	}

	@Override
	public boolean updateLicenseesRegistrationsm(LicenseesRegistrationsm licensee) {
		return DaoInitializationInterface.updateLicenseesRegistrationsm(licensee);
	}

	@Override
	public boolean updatefeetypes(FeeTypes feeTypes) {
		return DaoInitializationInterface.updatefeetypes(feeTypes);
	}
	@Override
	public boolean updatequestionaires(Questionnaire questionaire) {
		return DaoInitializationInterface.updatequestionaires(questionaire);
	}

	@Override
	public boolean updatefeemaster(FeeMaster feemaster) {
		return DaoInitializationInterface.updatefeemaster(feemaster);
	}

	@Override
	public boolean updatesuboccupancy(SubOccupancies suboccupancies) {
		return DaoInitializationInterface.updatesuboccupancy(suboccupancies);
	}

	@Override
	public boolean updateusages(Usages usages) {
		return DaoInitializationInterface.updateusages(usages);
	}

	@Override
	public boolean updateoccupancy(Occupancies occupancies) {
		return DaoInitializationInterface.updateoccupancy(occupancies);
	}

	@Override
	public List<Occupancies> listOccupancies() {
		return DaoInitializationInterface.listOccupancies();
	}

	@Override
	public List<FeeMaster> listFeeMaster() {
		return DaoInitializationInterface.listFeeMaster();
	}

	@Override
	public List<SubOccupancies> listSubOccupancy() {
		return DaoInitializationInterface.listSubOccupancy();
	}

	@Override
	public List<Usages> listUsages() {
		return DaoInitializationInterface.listUsages();
	}

	@Override
	public List<LicenseesRegistrationsm> listLicenseesRegistrationsms() {
		return DaoInitializationInterface.listLicenseesRegistrationsms();
	}

	@Override
	public List<FeeTypes> listFeeTypes() {
		return DaoInitializationInterface.listFeeTypes();
	}
	@Override
	public List<Questionnaire> listQuestionaires() {
		return DaoInitializationInterface.listQuestionaires();
	}

	@Override
	public boolean createLicenseeRegistration(Map<String, Object> param) {
		return DaoInitializationInterface.createLicenseeRegistration(param);
	}

	@Override
	public boolean initfeetypes(Map<String, Object> param) {
		return DaoInitializationInterface.initfeetypes(param);
	}
	@Override
	public boolean initQuestionaires(Map<String, Object> param) {
		return DaoInitializationInterface.initQuestionaires(param);
	}

	@Override
	public boolean initoccupancy(Map<String, Object> param) {
		return DaoInitializationInterface.initoccupancy(param);
	}

	@Override
	public boolean initfeemaster(Map<String, Object> param) {
		return DaoInitializationInterface.initfeemaster(param);
	}

	@Override
	public boolean initsuboccupancies(Map<String, Object> param) {
		return DaoInitializationInterface.initsuboccupancies(param);
	}

	@Override
	public boolean initusages(Map<String, Object> param) {
		return DaoInitializationInterface.initusages(param);
	}

	@Override
	public boolean checkExistance(String sql, Object[] values) {
		return DaoInitializationInterface.checkExistance(sql, values);
	}
	
	@Override
	public List<Offices> listOffices() {
    	return DaoInitializationInterface.listOffices();
	}
	
	@Override
	public  List<Offices> listOfficesAndQuestionaires(){
		List<Offices> list = listOffices();
		for (Offices office : list) {
			System.out.println("DAO"+office.getOfficecode());
			office.setMappedquestionaires(DaoInitializationInterface.getMappedQuestionaires(office.getOfficecode()));
		}
		return list;
		
	}
	
	@Override
	public String saveOfficeQuestioniares(List<Map<String, Object>> officesquestions) {
		return (DaoInitializationInterface.mapOfficesQuestioniares(officesquestions)) ? "Mapped" : "Failed";
	}
	
	@Override
	public boolean enableDisableStakeholder(String enable_disable,Integer usercode,String remarks,Integer usercode2,Long slno) {
		return DaoInitializationInterface.enableDisableStakeholder(enable_disable,usercode,remarks,usercode2,slno);
	}
}
