package obps.services;

import java.util.List;
import java.util.Map;

import obps.models.FeeMaster;
import obps.models.FeeTypes;
import obps.models.Filetypes;
import obps.models.LicenseesRegistrationsm;
import obps.models.Occupancies;
import obps.models.Offices;
import obps.models.Questionnaire;
import obps.models.SubOccupancies;
import obps.models.Usages;

public interface ServiceInitializationInterface {

	public Long getMaxLicenseecode();

	public Long getMaxFeeTypecode();

	public Long getMaxFileTypecode();

	public Long getMaxFeeCode();

	public boolean createLicenseeRegistration(Map<String, Object> param);

	public boolean initfeetypes(Map<String, Object> param);

	public boolean initfiletypes(Map<String, Object> param);

	public boolean initQuestionaires(Map<String, Object> param);

	public boolean initoccupancy(Map<String, Object> param);

	public boolean initfeemaster(Map<String, Object> param);

	public boolean initsuboccupancies(Map<String, Object> param);

	public boolean initusages(Map<String, Object> param);

	public boolean checkExistance(String sql, Object[] values);

	public boolean updateLicenseesRegistrationsm(LicenseesRegistrationsm licensee);

	public boolean updatefeetypes(FeeTypes feetype);

	public boolean updatefiletypes(Filetypes filetype);

	public boolean updatequestionaires(Questionnaire questionaire);

	public boolean updatefeemaster(FeeMaster feemaster);

	public boolean updatesuboccupancy(SubOccupancies suboccupancies);

	public boolean updateusages(Usages usages);

	public boolean updateoccupancy(Occupancies occupancy);

	public List<Occupancies> listOccupancies();

	public List<FeeMaster> listFeeMaster();

	public List<SubOccupancies> listSubOccupancy();

	public List<Usages> listUsages();

	public List<LicenseesRegistrationsm> listLicenseesRegistrationsms();

	public List<FeeTypes> listFeeTypes();

	public List<Filetypes> listFileTypes();

	public List<Questionnaire> listQuestionaires();

	public List<Offices> listOfficesAndQuestionaires();

	public List<Offices> listOffices();

	public String saveOfficeQuestioniares(List<Map<String, Object>> officesquestions);

}
