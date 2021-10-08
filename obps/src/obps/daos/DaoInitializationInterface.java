package obps.daos;

import java.util.List;
import java.util.Map;

import obps.models.FeeMaster;
import obps.models.FeeTypes;
import obps.models.Filetypes;
import obps.models.LicenseesRegistrationsm;
import obps.models.Occupancies;
import obps.models.Offices;
import obps.models.PaymentModes;
import obps.models.Questionnaire;
import obps.models.SubOccupancies;
import obps.models.Usages;

public interface DaoInitializationInterface {

	public List<Occupancies> listOccupancies();

	public List<FeeMaster> listFeeMaster();

	public List<SubOccupancies> listSubOccupancy();

	public List<Usages> listUsages();

	public List<LicenseesRegistrationsm> listLicenseesRegistrationsms();

	public List<FeeTypes> listFeeTypes();

	public List<Filetypes> listFileTypes();

	public List<Questionnaire> listQuestionaires();

	public boolean updateLicenseesRegistrationsm(LicenseesRegistrationsm licensee);

	public boolean updatefeetypes(FeeTypes feetype);

	public boolean updatefiletypes(Filetypes feetype);

	public boolean updatequestionaires(Questionnaire questionaire);

	public boolean updatefeemaster(FeeMaster feemaster);

	public boolean updatesuboccupancy(SubOccupancies suboccupancies);

	public boolean updateusages(Usages usages);

	public boolean updateoccupancy(Occupancies occupancies);

	public boolean createLicenseeRegistration(Map<String, Object> param);

	public boolean initfeetypes(Map<String, Object> param);

	public boolean initfiletypes(Map<String, Object> param);

	public boolean initQuestionaires(Map<String, Object> param);

	public boolean initoccupancy(Map<String, Object> param);

	public boolean initfeemaster(Map<String, Object> param);

	public boolean initsuboccupancies(Map<String, Object> param);

	public boolean initusages(Map<String, Object> param);

	public boolean checkExistance(String sql, Object[] values);

	public List<Offices> listOffices();

	public List<Questionnaire> getMappedQuestionaires(Integer officecode);

	public boolean mapOfficesQuestioniares(List<Map<String, Object>> officesquestions);

}
