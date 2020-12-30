
CREATE TABLE [dbo].[Users](
	[Id] [bigint] IDENTITY(1,1) NOT NULL,
	[ChangePasswordOnLogon] [bit] NULL,
	[PictureId] [bigint] NULL,
	[WorkspaceId] [bigint] NULL,
	[UserSerial] [bigint] NULL,
	[AdhocSerial] [bigint] NULL,
	[Username] [nvarchar](61) NULL,
	[Password] [nvarchar](100) NULL,
	[Role] [nvarchar](60) NULL,
	[ResetKey] [uniqueidentifier] NULL,
	[Name] [nvarchar](50) NULL,
	[Address1] [nvarchar](200) NULL,
	[Address2] [nvarchar](200) NULL,
	[Address3] [nvarchar](200) NULL,
	[CityId] [bigint] NULL,
	[StateId] [bigint] NULL,
	[Country] [nvarchar](max) NULL,
	[PhoneNo] [nvarchar](15) NULL,
	[Mobile] [nvarchar](15) NULL,
	[Email] [nvarchar](60) NULL,
	[ContactPerson] [nvarchar](30) NULL,
	[AwacsMTD] [bit] NULL,
	[IsLiveorderBlocked] [bit] NULL,
	[IsVerify] [bit] NULL,
	[IsAdmin] [bit] NULL,
	[IsAdminDelegate] [bit] NULL,
	[IsAdhoc] [bit] NULL,
	[DeviceId] [nvarchar](50) NULL,
	[GCMRegisterKey] [nvarchar](max) NULL,
	[Type] [nvarchar](50) NULL,
	[LoginType] [nvarchar](50) NULL,
	[ProviderId] [bigint] NULL,
	[ProviderType] [nvarchar](50) NULL,
	[AwacsId] [nvarchar](10) NULL,
	[PSPACode] [nvarchar](61) NULL,
	[UniqueMobileCode] [nvarchar](11) NULL,
	[IsSyncWithEdxhub] [bit] NULL,
	[ThirdPartySoftware] [nvarchar](20) NULL,
	[ThirdPartyCode] [nvarchar](61) NULL,
	[ThirdPartyLUM] [datetime] NULL,
	[ThirdPartyLUT] [datetime] NULL,
	[IsSyncWithThirdParty] [bit] NULL,
	[Dlic1] [nvarchar](100) NULL,
	[Dlic2] [nvarchar](100) NULL,
	[Dlic3] [nvarchar](20) NULL,
	[Dlic4] [nvarchar](20) NULL,
	[Dlic5] [nvarchar](20) NULL,
	[Dlic6] [nvarchar](60) NULL,
	[POBOTP] [nvarchar](6) NULL,
	[GSTIN] [nvarchar](16) NULL,
	[PAN] [nvarchar](20) NULL,
	[XSource] [nvarchar](35) NULL,
	[Pincode] [nvarchar](10) NULL,
	[Remarks] [nvarchar](500) NULL,
	[RegistrationId] [nvarchar](50) NULL,
	[Source] [nvarchar](60) NULL,
	[SourceType] [nvarchar](30) NULL,
	[SignSource] [nvarchar](10) NULL,
	[SignDetail] [nvarchar](50) NULL,
	[CreatedBy] [bigint] NULL,
	[CreatedOn] [datetime] NULL,
	[ModifiedBy] [bigint] NULL,
	[ModifiedOn] [datetime] NULL,
	[Deleted] [bit] NULL,
	[DeletedBy] [bigint] NULL,
	[DeletedOn] [datetime] NULL,
	[IsLicenseExpire] [bit] NULL,
	[LicenseExpireMailOn] [datetime] NULL,
	[Sync] [bit] NULL,
	[LastActivity] [datetime] NULL,
	[OrderSeq] [bigint] NULL,
	[AppVersion] [nvarchar](25) NULL,
	[OSVersion] [nvarchar](25) NULL,
	[Platform] [nvarchar](25) NULL,
	[UserKind] [nvarchar](15) NULL,
	[Bank] [nvarchar](10) NULL,
	[IFSCCode] [nvarchar](15) NULL,
	[CorpId] [nvarchar](32) NULL,
	[UserId] [nvarchar](32) NULL,
	[AccountNumber] [nvarchar](20) NULL,
	[AppPackage] [nvarchar](max) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]


drop table if exists dbo.hibernate_sequence;

create table dbo.hibernate_sequence (next_val bigint);
insert into dbo.hibernate_sequence values (1000);

drop table if exists dbo.oauth_client_details;

create table dbo.oauth_client_details (
  client_id varchar(255) primary key,
  resource_ids varchar(255),
  client_secret varchar(255),
  scope varchar(255),
  authorized_grant_types varchar(255),
  web_server_redirect_uri varchar(255),
  authorities varchar(255),
  access_token_validity integer,
  refresh_token_validity integer,
  additional_information varchar(4096),
  autoapprove varchar(255),
  created datetime default GetDate(),
  updated datetime default GetDate()
);

drop table if exists dbo.oauth_client_token;

create table dbo.oauth_client_token (
  token_id varchar(255),
  token varbinary,
  authentication_id varchar(255) primary key,
  user_name varchar(255),
  client_id varchar(255),
  created datetime default GetDate(),
  updated datetime default GetDate()
);

drop table if exists dbo.oauth_access_token;
create table dbo.oauth_access_token (
  token_id varchar(255),
  token varbinary,
  authentication_id varchar(255) primary key,
  user_name varchar(255),
  client_id varchar(255),
  authentication varbinary,
  refresh_token varchar(255),
  created datetime default GetDate(),
  updated datetime default GetDate()
);

drop table if exists dbo.oauth_refresh_token;
create table dbo.oauth_refresh_token (
  token_id varchar(255),
  token varbinary,
  authentication varbinary,
  created datetime default GetDate(),
  updated datetime default GetDate()
);

drop table if exists dbo.oauth_code;
create table dbo.oauth_code (
  code varchar(255),
  authentication varbinary,
  created datetime default GetDate(),
  updated datetime default GetDate()
);

drop table if exists dbo.oauth_approvals;
create table dbo.oauth_approvals (
  userid varchar(255),
  clientid varchar(255),
  scope varchar(255),
  status varchar(10),
  expiresat timestamp,
  lastmodifiedat datetime default GetDate(),
  created datetime default GetDate(),
  updated datetime default GetDate()
);

drop table if exists dbo.users_authorities;
create table dbo.users_authorities (
   user_id bigint not null identity(1,1),
   authority_id bigint not null,
  created datetime default GetDate(),
  updated datetime default GetDate(),
  primary key (user_id, authority_id)
);


drop table if exists dbo.authority;
create table dbo.authority (
   id bigint not null identity(1,1) primary key,
   name varchar(255) unique,
   created datetime default GetDate(),
   updated datetime default GetDate()
);


drop table if exists dbo.awacs_user;
create table dbo.awacs_user (
  id bigint not null identity(1,1) primary key,
  email varchar(255) default null,
  msisdn varchar(255) default null,
  user_name varchar(255) default null,
  password varchar(255) default null,
  aadhar varchar (255) default null,
  confirmation_token varchar (255) default null,
  account_expired tinyint default null,
  account_locked tinyint default null,
  credentials_expired tinyint default null,
  enabled tinyint default null,
  created datetime default GetDate(),
  updated datetime default GetDate()
);

alter table dbo.awacs_user add constraint user_username unique(user_name);
alter table dbo.awacs_user add constraint user_username_email_msisdn unique(user_name, email, msisdn);
alter table dbo.awacs_user add constraint user_username_msisdn_aadhar unique(msisdn, aadhar);

alter table dbo.users_authorities add constraint users_authorities_authority foreign key (authority_id) references authority(id) on delete cascade;

-- alter table dbo.users_authorities add constraint users_authorities_awacs_user foreign key (awacs_user_id) references awacs_user(id) on delete cascade;
