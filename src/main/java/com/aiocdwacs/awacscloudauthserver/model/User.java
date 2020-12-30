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
	private Long id;
	private boolean changePasswordOnLogon;
	private Long pictureId;
	private Long workspaceId;
	private Long userSerial;
	private Long adhocSerial;
	private String username;
	private String password;
	private String role;
	private String resetKey;
	private String name;
	private String address1;
	private String address2;
	private String address3;
	private Long cityId;
	private Long stateId;
	private String country;
	private String phoneNo;
	private String mobile;
	private String email;
	private String contactPerson;
	private boolean awacsMTD;
	private boolean isLiveorderBlocked;
	private boolean isVerify;
	private boolean isAdmin;
	private String isAdminDelegate;
	private String isAdhoc;
	private String deviceId;
	private String gCMRegisterKey;
	private String type;
	private String loginType;
	private Long providerId;
	private String providerType;
	private String awacsId;
	private String pSPACode;
	private String uniqueMobileCode;
	private boolean isSyncWithEdxhub;
	private String thirdPartySoftware;
	private String thirdPartyCode;
	private LocalDateTime thirdPartyLUM;
	private LocalDateTime thirdPartyLUT;
	private boolean isSyncWithThirdParty;
	private String dlic1;
	private String dlic2;
	private String dlic3;
	private String dlic4;
	private String dlic5;
	private String dlic6;
	private String pOBOTP;
	private String gSTIN;
	private String pAN;
	private String xSource;
	private String pincode;
	private String remarks;
	private String registrationId;
	private String source;
	private String sourceType;
	private String signSource;
	private String signDetail;
	private Long createdBy;
	private LocalDateTime createdOn;
	private Long modifiedBy;
	private LocalDateTime modifiedOn;
	private String deleted;
	private Long deletedBy;
	private LocalDateTime deletedOn;
	private boolean isLicenseExpire;
	private LocalDateTime licenseExpireMailOn;
	private boolean sync;
	private LocalDateTime lastActivity;
	private Long orderSeq;
	private String appVersion;
	private String oSVersion;
	private String platform;
	private String userKind;
	private String bank;
	private String iFSCCode;
	private String corpId;
	private String userId;
	private String accountNumber;
	private String appPackage;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "users_authorities", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "Id"), inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
	@OrderBy
	@JsonIgnore
	private Collection<Authority> authorities;

	public User() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public boolean isChangePasswordOnLogon() {
		return changePasswordOnLogon;
	}


	public void setChangePasswordOnLogon(boolean changePasswordOnLogon) {
		this.changePasswordOnLogon = changePasswordOnLogon;
	}


	public Long getPictureId() {
		return pictureId;
	}


	public void setPictureId(Long pictureId) {
		this.pictureId = pictureId;
	}


	public Long getWorkspaceId() {
		return workspaceId;
	}


	public void setWorkspaceId(Long workspaceId) {
		this.workspaceId = workspaceId;
	}


	public Long getUserSerial() {
		return userSerial;
	}


	public void setUserSerial(Long userSerial) {
		this.userSerial = userSerial;
	}


	public Long getAdhocSerial() {
		return adhocSerial;
	}


	public void setAdhocSerial(Long adhocSerial) {
		this.adhocSerial = adhocSerial;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public String getResetKey() {
		return resetKey;
	}


	public void setResetKey(String resetKey) {
		this.resetKey = resetKey;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getAddress1() {
		return address1;
	}


	public void setAddress1(String address1) {
		this.address1 = address1;
	}


	public String getAddress2() {
		return address2;
	}


	public void setAddress2(String address2) {
		this.address2 = address2;
	}


	public String getAddress3() {
		return address3;
	}


	public void setAddress3(String address3) {
		this.address3 = address3;
	}


	public Long getCityId() {
		return cityId;
	}


	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}


	public Long getStateId() {
		return stateId;
	}


	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getPhoneNo() {
		return phoneNo;
	}


	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getContactPerson() {
		return contactPerson;
	}


	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}


	public boolean isAwacsMTD() {
		return awacsMTD;
	}


	public void setAwacsMTD(boolean awacsMTD) {
		this.awacsMTD = awacsMTD;
	}


	public boolean isLiveorderBlocked() {
		return isLiveorderBlocked;
	}


	public void setLiveorderBlocked(boolean isLiveorderBlocked) {
		this.isLiveorderBlocked = isLiveorderBlocked;
	}


	public boolean isVerify() {
		return isVerify;
	}


	public void setVerify(boolean isVerify) {
		this.isVerify = isVerify;
	}


	public boolean isAdmin() {
		return isAdmin;
	}


	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}


	public String getIsAdminDelegate() {
		return isAdminDelegate;
	}


	public void setIsAdminDelegate(String isAdminDelegate) {
		this.isAdminDelegate = isAdminDelegate;
	}


	public String getIsAdhoc() {
		return isAdhoc;
	}


	public void setIsAdhoc(String isAdhoc) {
		this.isAdhoc = isAdhoc;
	}


	public String getDeviceId() {
		return deviceId;
	}


	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}


	public String getgCMRegisterKey() {
		return gCMRegisterKey;
	}


	public void setgCMRegisterKey(String gCMRegisterKey) {
		this.gCMRegisterKey = gCMRegisterKey;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getLoginType() {
		return loginType;
	}


	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}


	public Long getProviderId() {
		return providerId;
	}


	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}


	public String getProviderType() {
		return providerType;
	}


	public void setProviderType(String providerType) {
		this.providerType = providerType;
	}


	public String getAwacsId() {
		return awacsId;
	}


	public void setAwacsId(String awacsId) {
		this.awacsId = awacsId;
	}


	public String getpSPACode() {
		return pSPACode;
	}


	public void setpSPACode(String pSPACode) {
		this.pSPACode = pSPACode;
	}


	public String getUniqueMobileCode() {
		return uniqueMobileCode;
	}


	public void setUniqueMobileCode(String uniqueMobileCode) {
		this.uniqueMobileCode = uniqueMobileCode;
	}


	public boolean isSyncWithEdxhub() {
		return isSyncWithEdxhub;
	}


	public void setSyncWithEdxhub(boolean isSyncWithEdxhub) {
		this.isSyncWithEdxhub = isSyncWithEdxhub;
	}


	public String getThirdPartySoftware() {
		return thirdPartySoftware;
	}


	public void setThirdPartySoftware(String thirdPartySoftware) {
		this.thirdPartySoftware = thirdPartySoftware;
	}


	public String getThirdPartyCode() {
		return thirdPartyCode;
	}


	public void setThirdPartyCode(String thirdPartyCode) {
		this.thirdPartyCode = thirdPartyCode;
	}


	public LocalDateTime getThirdPartyLUM() {
		return thirdPartyLUM;
	}


	public void setThirdPartyLUM(LocalDateTime thirdPartyLUM) {
		this.thirdPartyLUM = thirdPartyLUM;
	}


	public LocalDateTime getThirdPartyLUT() {
		return thirdPartyLUT;
	}


	public void setThirdPartyLUT(LocalDateTime thirdPartyLUT) {
		this.thirdPartyLUT = thirdPartyLUT;
	}


	public boolean isSyncWithThirdParty() {
		return isSyncWithThirdParty;
	}


	public void setSyncWithThirdParty(boolean isSyncWithThirdParty) {
		this.isSyncWithThirdParty = isSyncWithThirdParty;
	}


	public String getDlic1() {
		return dlic1;
	}


	public void setDlic1(String dlic1) {
		this.dlic1 = dlic1;
	}


	public String getDlic2() {
		return dlic2;
	}


	public void setDlic2(String dlic2) {
		this.dlic2 = dlic2;
	}


	public String getDlic3() {
		return dlic3;
	}


	public void setDlic3(String dlic3) {
		this.dlic3 = dlic3;
	}


	public String getDlic4() {
		return dlic4;
	}


	public void setDlic4(String dlic4) {
		this.dlic4 = dlic4;
	}


	public String getDlic5() {
		return dlic5;
	}


	public void setDlic5(String dlic5) {
		this.dlic5 = dlic5;
	}


	public String getDlic6() {
		return dlic6;
	}


	public void setDlic6(String dlic6) {
		this.dlic6 = dlic6;
	}


	public String getpOBOTP() {
		return pOBOTP;
	}


	public void setpOBOTP(String pOBOTP) {
		this.pOBOTP = pOBOTP;
	}


	public String getgSTIN() {
		return gSTIN;
	}


	public void setgSTIN(String gSTIN) {
		this.gSTIN = gSTIN;
	}


	public String getpAN() {
		return pAN;
	}


	public void setpAN(String pAN) {
		this.pAN = pAN;
	}


	public String getxSource() {
		return xSource;
	}


	public void setxSource(String xSource) {
		this.xSource = xSource;
	}


	public String getPincode() {
		return pincode;
	}


	public void setPincode(String pincode) {
		this.pincode = pincode;
	}


	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public String getRegistrationId() {
		return registrationId;
	}


	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}


	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}


	public String getSourceType() {
		return sourceType;
	}


	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}


	public String getSignSource() {
		return signSource;
	}


	public void setSignSource(String signSource) {
		this.signSource = signSource;
	}


	public String getSignDetail() {
		return signDetail;
	}


	public void setSignDetail(String signDetail) {
		this.signDetail = signDetail;
	}


	public Long getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}


	public LocalDateTime getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}


	public Long getModifiedBy() {
		return modifiedBy;
	}


	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public LocalDateTime getModifiedOn() {
		return modifiedOn;
	}


	public void setModifiedOn(LocalDateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
	}


	public String getDeleted() {
		return deleted;
	}


	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}


	public Long getDeletedBy() {
		return deletedBy;
	}


	public void setDeletedBy(Long deletedBy) {
		this.deletedBy = deletedBy;
	}


	public LocalDateTime getDeletedOn() {
		return deletedOn;
	}


	public void setDeletedOn(LocalDateTime deletedOn) {
		this.deletedOn = deletedOn;
	}


	public boolean isLicenseExpire() {
		return isLicenseExpire;
	}


	public void setLicenseExpire(boolean isLicenseExpire) {
		this.isLicenseExpire = isLicenseExpire;
	}


	public LocalDateTime getLicenseExpireMailOn() {
		return licenseExpireMailOn;
	}


	public void setLicenseExpireMailOn(LocalDateTime licenseExpireMailOn) {
		this.licenseExpireMailOn = licenseExpireMailOn;
	}


	public boolean isSync() {
		return sync;
	}


	public void setSync(boolean sync) {
		this.sync = sync;
	}


	public LocalDateTime getLastActivity() {
		return lastActivity;
	}


	public void setLastActivity(LocalDateTime lastActivity) {
		this.lastActivity = lastActivity;
	}


	public Long getOrderSeq() {
		return orderSeq;
	}


	public void setOrderSeq(Long orderSeq) {
		this.orderSeq = orderSeq;
	}


	public String getAppVersion() {
		return appVersion;
	}


	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}


	public String getoSVersion() {
		return oSVersion;
	}


	public void setoSVersion(String oSVersion) {
		this.oSVersion = oSVersion;
	}


	public String getPlatform() {
		return platform;
	}


	public void setPlatform(String platform) {
		this.platform = platform;
	}


	public String getUserKind() {
		return userKind;
	}


	public void setUserKind(String userKind) {
		this.userKind = userKind;
	}


	public String getBank() {
		return bank;
	}


	public void setBank(String bank) {
		this.bank = bank;
	}


	public String getiFSCCode() {
		return iFSCCode;
	}


	public void setiFSCCode(String iFSCCode) {
		this.iFSCCode = iFSCCode;
	}


	public String getCorpId() {
		return corpId;
	}


	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getAccountNumber() {
		return accountNumber;
	}


	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}


	public String getAppPackage() {
		return appPackage;
	}


	public void setAppPackage(String appPackage) {
		this.appPackage = appPackage;
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