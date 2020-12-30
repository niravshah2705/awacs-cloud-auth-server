package com.aiocdwacs.awacscloudauthserver.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Users", uniqueConstraints = { @UniqueConstraint(columnNames = { "Username" }) })
public class User implements UserDetails, Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

	private boolean ChangePasswordOnLogon;
	private Long PictureId;
	private Long WorkspaceId;
	private Long UserSerial;
	private Long AdhocSerial;
	private String Username;
	private String Password;
	private String Role;
	private String ResetKey;
	private String Name;
	private String Address1;
	private String Address2;
	private String Address3;
	private Long CityId;
	private Long StateId;
	private String Country;
	private String PhoneNo;
	private String Mobile;
	private String Email;
	private String ContactPerson;
	private boolean AwacsMTD;
	private boolean IsLiveorderBlocked;
	private boolean IsVerify;
	private boolean IsAdmin;
	private String IsAdminDelegate;
	private String IsAdhoc;
	private String DeviceId;
	private String GCMRegisterKey;
	private String Type;
	private String LoginType;
	private Long ProviderId;
	private String ProviderType;
	private String AwacsId;
	private String PSPACode;
	private String UniqueMobileCode;
	private boolean IsSyncWithEdxhub;
	private String ThirdPartySoftware;
	private String ThirdPartyCode;
	private LocalDateTime ThirdPartyLUM;
	private LocalDateTime ThirdPartyLUT;
	private boolean IsSyncWithThirdParty;
	private String Dlic1;
	private String Dlic2;
	private String Dlic3;
	private String Dlic4;
	private String Dlic5;
	private String Dlic6;
	private String POBOTP;
	private String GSTIN;
	private String PAN;
	private String XSource;
	private String Pincode;
	private String Remarks;
	private String RegistrationId;
	private String Source;
	private String SourceType;
	private String SignSource;
	private String SignDetail;
	private Long CreatedBy;
	private LocalDateTime CreatedOn;
	private Long ModifiedBy;
	private LocalDateTime ModifiedOn;
	private String Deleted;
	private Long DeletedBy;
	private LocalDateTime DeletedOn;
	private boolean IsLicenseExpire;
	private LocalDateTime LicenseExpireMailOn;
	private boolean Sync;
	private LocalDateTime LastActivity;
	private Long OrderSeq;
	private String AppVersion;
	private String OSVersion;
	private String Platform;
	private String UserKind;
	private String Bank;
	private String IFSCCode;
	private String CorpId;
	private String UserId;
	private String AccountNumber;
	private String AppPackage;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "users_authorities", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "Id"), inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
	@OrderBy
	@JsonIgnore
	private Collection<Authority> authorities;

	public User() {
		super();
	}
	
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}


	public boolean isChangePasswordOnLogon() {
		return ChangePasswordOnLogon;
	}


	public void setChangePasswordOnLogon(boolean changePasswordOnLogon) {
		ChangePasswordOnLogon = changePasswordOnLogon;
	}


	public Long getPictureId() {
		return PictureId;
	}


	public void setPictureId(Long pictureId) {
		PictureId = pictureId;
	}


	public Long getWorkspaceId() {
		return WorkspaceId;
	}


	public void setWorkspaceId(Long workspaceId) {
		WorkspaceId = workspaceId;
	}


	public Long getUserSerial() {
		return UserSerial;
	}


	public void setUserSerial(Long userSerial) {
		UserSerial = userSerial;
	}


	public Long getAdhocSerial() {
		return AdhocSerial;
	}


	public void setAdhocSerial(Long adhocSerial) {
		AdhocSerial = adhocSerial;
	}


	public String getUsername() {
		return Username;
	}


	public void setUsername(String username) {
		Username = username;
	}


	public String getPassword() {
		return Password;
	}


	public void setPassword(String password) {
		Password = password;
	}


	public String getRole() {
		return Role;
	}


	public void setRole(String role) {
		Role = role;
	}


	public String getResetKey() {
		return ResetKey;
	}


	public void setResetKey(String resetKey) {
		ResetKey = resetKey;
	}


	public String getName() {
		return Name;
	}


	public void setName(String name) {
		Name = name;
	}


	public String getAddress1() {
		return Address1;
	}


	public void setAddress1(String address1) {
		Address1 = address1;
	}


	public String getAddress2() {
		return Address2;
	}


	public void setAddress2(String address2) {
		Address2 = address2;
	}


	public String getAddress3() {
		return Address3;
	}


	public void setAddress3(String address3) {
		Address3 = address3;
	}


	public Long getCityId() {
		return CityId;
	}


	public void setCityId(Long cityId) {
		CityId = cityId;
	}


	public Long getStateId() {
		return StateId;
	}


	public void setStateId(Long stateId) {
		StateId = stateId;
	}


	public String getCountry() {
		return Country;
	}


	public void setCountry(String country) {
		Country = country;
	}


	public String getPhoneNo() {
		return PhoneNo;
	}


	public void setPhoneNo(String phoneNo) {
		PhoneNo = phoneNo;
	}


	public String getMobile() {
		return Mobile;
	}


	public void setMobile(String mobile) {
		Mobile = mobile;
	}


	public String getEmail() {
		return Email;
	}


	public void setEmail(String email) {
		Email = email;
	}


	public String getContactPerson() {
		return ContactPerson;
	}


	public void setContactPerson(String contactPerson) {
		ContactPerson = contactPerson;
	}


	public boolean isAwacsMTD() {
		return AwacsMTD;
	}


	public void setAwacsMTD(boolean awacsMTD) {
		AwacsMTD = awacsMTD;
	}


	public boolean isIsLiveorderBlocked() {
		return IsLiveorderBlocked;
	}


	public void setIsLiveorderBlocked(boolean isLiveorderBlocked) {
		IsLiveorderBlocked = isLiveorderBlocked;
	}


	public boolean isIsVerify() {
		return IsVerify;
	}


	public void setIsVerify(boolean isVerify) {
		IsVerify = isVerify;
	}


	public boolean isIsAdmin() {
		return IsAdmin;
	}


	public void setIsAdmin(boolean isAdmin) {
		IsAdmin = isAdmin;
	}


	public String getIsAdminDelegate() {
		return IsAdminDelegate;
	}


	public void setIsAdminDelegate(String isAdminDelegate) {
		IsAdminDelegate = isAdminDelegate;
	}


	public String getIsAdhoc() {
		return IsAdhoc;
	}


	public void setIsAdhoc(String isAdhoc) {
		IsAdhoc = isAdhoc;
	}


	public String getDeviceId() {
		return DeviceId;
	}


	public void setDeviceId(String deviceId) {
		DeviceId = deviceId;
	}


	public String getGCMRegisterKey() {
		return GCMRegisterKey;
	}


	public void setGCMRegisterKey(String gCMRegisterKey) {
		GCMRegisterKey = gCMRegisterKey;
	}


	public String getType() {
		return Type;
	}


	public void setType(String type) {
		Type = type;
	}


	public String getLoginType() {
		return LoginType;
	}


	public void setLoginType(String loginType) {
		LoginType = loginType;
	}


	public Long getProviderId() {
		return ProviderId;
	}


	public void setProviderId(Long providerId) {
		ProviderId = providerId;
	}


	public String getProviderType() {
		return ProviderType;
	}


	public void setProviderType(String providerType) {
		ProviderType = providerType;
	}


	public String getAwacsId() {
		return AwacsId;
	}


	public void setAwacsId(String awacsId) {
		AwacsId = awacsId;
	}


	public String getPSPACode() {
		return PSPACode;
	}


	public void setPSPACode(String pSPACode) {
		PSPACode = pSPACode;
	}


	public String getUniqueMobileCode() {
		return UniqueMobileCode;
	}


	public void setUniqueMobileCode(String uniqueMobileCode) {
		UniqueMobileCode = uniqueMobileCode;
	}


	public boolean isIsSyncWithEdxhub() {
		return IsSyncWithEdxhub;
	}


	public void setIsSyncWithEdxhub(boolean isSyncWithEdxhub) {
		IsSyncWithEdxhub = isSyncWithEdxhub;
	}


	public String getThirdPartySoftware() {
		return ThirdPartySoftware;
	}


	public void setThirdPartySoftware(String thirdPartySoftware) {
		ThirdPartySoftware = thirdPartySoftware;
	}


	public String getThirdPartyCode() {
		return ThirdPartyCode;
	}


	public void setThirdPartyCode(String thirdPartyCode) {
		ThirdPartyCode = thirdPartyCode;
	}


	public LocalDateTime getThirdPartyLUM() {
		return ThirdPartyLUM;
	}


	public void setThirdPartyLUM(LocalDateTime thirdPartyLUM) {
		ThirdPartyLUM = thirdPartyLUM;
	}


	public LocalDateTime getThirdPartyLUT() {
		return ThirdPartyLUT;
	}


	public void setThirdPartyLUT(LocalDateTime thirdPartyLUT) {
		ThirdPartyLUT = thirdPartyLUT;
	}


	public boolean isIsSyncWithThirdParty() {
		return IsSyncWithThirdParty;
	}


	public void setIsSyncWithThirdParty(boolean isSyncWithThirdParty) {
		IsSyncWithThirdParty = isSyncWithThirdParty;
	}


	public String getDlic1() {
		return Dlic1;
	}


	public void setDlic1(String dlic1) {
		Dlic1 = dlic1;
	}


	public String getDlic2() {
		return Dlic2;
	}


	public void setDlic2(String dlic2) {
		Dlic2 = dlic2;
	}


	public String getDlic3() {
		return Dlic3;
	}


	public void setDlic3(String dlic3) {
		Dlic3 = dlic3;
	}


	public String getDlic4() {
		return Dlic4;
	}


	public void setDlic4(String dlic4) {
		Dlic4 = dlic4;
	}


	public String getDlic5() {
		return Dlic5;
	}


	public void setDlic5(String dlic5) {
		Dlic5 = dlic5;
	}


	public String getDlic6() {
		return Dlic6;
	}


	public void setDlic6(String dlic6) {
		Dlic6 = dlic6;
	}


	public String getPOBOTP() {
		return POBOTP;
	}


	public void setPOBOTP(String pOBOTP) {
		POBOTP = pOBOTP;
	}


	public String getGSTIN() {
		return GSTIN;
	}


	public void setGSTIN(String gSTIN) {
		GSTIN = gSTIN;
	}


	public String getPAN() {
		return PAN;
	}


	public void setPAN(String pAN) {
		PAN = pAN;
	}


	public String getXSource() {
		return XSource;
	}


	public void setXSource(String xSource) {
		XSource = xSource;
	}


	public String getPincode() {
		return Pincode;
	}


	public void setPincode(String pincode) {
		Pincode = pincode;
	}


	public String getRemarks() {
		return Remarks;
	}


	public void setRemarks(String remarks) {
		Remarks = remarks;
	}


	public String getRegistrationId() {
		return RegistrationId;
	}


	public void setRegistrationId(String registrationId) {
		RegistrationId = registrationId;
	}


	public String getSource() {
		return Source;
	}


	public void setSource(String source) {
		Source = source;
	}


	public String getSourceType() {
		return SourceType;
	}


	public void setSourceType(String sourceType) {
		SourceType = sourceType;
	}


	public String getSignSource() {
		return SignSource;
	}


	public void setSignSource(String signSource) {
		SignSource = signSource;
	}


	public String getSignDetail() {
		return SignDetail;
	}


	public void setSignDetail(String signDetail) {
		SignDetail = signDetail;
	}


	public Long getCreatedBy() {
		return CreatedBy;
	}


	public void setCreatedBy(Long createdBy) {
		CreatedBy = createdBy;
	}


	public LocalDateTime getCreatedOn() {
		return CreatedOn;
	}


	public void setCreatedOn(LocalDateTime createdOn) {
		CreatedOn = createdOn;
	}


	public Long getModifiedBy() {
		return ModifiedBy;
	}


	public void setModifiedBy(Long modifiedBy) {
		ModifiedBy = modifiedBy;
	}


	public LocalDateTime getModifiedOn() {
		return ModifiedOn;
	}


	public void setModifiedOn(LocalDateTime modifiedOn) {
		ModifiedOn = modifiedOn;
	}


	public String getDeleted() {
		return Deleted;
	}


	public void setDeleted(String deleted) {
		Deleted = deleted;
	}


	public Long getDeletedBy() {
		return DeletedBy;
	}


	public void setDeletedBy(Long deletedBy) {
		DeletedBy = deletedBy;
	}


	public LocalDateTime getDeletedOn() {
		return DeletedOn;
	}


	public void setDeletedOn(LocalDateTime deletedOn) {
		DeletedOn = deletedOn;
	}


	public boolean isIsLicenseExpire() {
		return IsLicenseExpire;
	}


	public void setIsLicenseExpire(boolean isLicenseExpire) {
		IsLicenseExpire = isLicenseExpire;
	}


	public LocalDateTime getLicenseExpireMailOn() {
		return LicenseExpireMailOn;
	}


	public void setLicenseExpireMailOn(LocalDateTime licenseExpireMailOn) {
		LicenseExpireMailOn = licenseExpireMailOn;
	}


	public boolean isSync() {
		return Sync;
	}


	public void setSync(boolean sync) {
		Sync = sync;
	}


	public LocalDateTime getLastActivity() {
		return LastActivity;
	}


	public void setLastActivity(LocalDateTime lastActivity) {
		LastActivity = lastActivity;
	}


	public Long getOrderSeq() {
		return OrderSeq;
	}


	public void setOrderSeq(Long orderSeq) {
		OrderSeq = orderSeq;
	}


	public String getAppVersion() {
		return AppVersion;
	}


	public void setAppVersion(String appVersion) {
		AppVersion = appVersion;
	}


	public String getOSVersion() {
		return OSVersion;
	}


	public void setOSVersion(String oSVersion) {
		OSVersion = oSVersion;
	}


	public String getPlatform() {
		return Platform;
	}


	public void setPlatform(String platform) {
		Platform = platform;
	}


	public String getUserKind() {
		return UserKind;
	}


	public void setUserKind(String userKind) {
		UserKind = userKind;
	}


	public String getBank() {
		return Bank;
	}


	public void setBank(String bank) {
		Bank = bank;
	}


	public String getIFSCCode() {
		return IFSCCode;
	}


	public void setIFSCCode(String iFSCCode) {
		IFSCCode = iFSCCode;
	}


	public String getCorpId() {
		return CorpId;
	}


	public void setCorpId(String corpId) {
		CorpId = corpId;
	}


	public String getUserId() {
		return UserId;
	}


	public void setUserId(String userId) {
		UserId = userId;
	}


	public String getAccountNumber() {
		return AccountNumber;
	}


	public void setAccountNumber(String accountNumber) {
		AccountNumber = accountNumber;
	}


	public String getAppPackage() {
		return AppPackage;
	}


	public void setAppPackage(String appPackage) {
		AppPackage = appPackage;
	}

	public Collection<Authority> getAuthorities() {
		return authorities;
	}


	public void setAuthorities(Collection<Authority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}
}