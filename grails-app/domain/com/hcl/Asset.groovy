package com.hcl

class Asset implements Serializable {
	
	String assetNumber
	String assetParent
	String description
	String status
	String assetType
	String vendor
	String manufacturer
	double purchasePrice
	double replaceCost
	Date installDate
	Date expiryDate
	double grossCost
	boolean isActive
	String itemId
	double grossDownTime
	Date lastUpdated
	String updatedBy
	boolean isDisabled
	String siteId
	String companyId
	String itemSetId
	double toolRate
	Date dateCreated
	String fAddressCode
	Location location

	static constraints = {
		assetNumber blank:false, maxSize: 25
		description maxSize: 100, nullable: true
		status blank:false, maxSize: 20
		assetType blank:false, maxSize: 15
		location nullable: true
		assetParent maxSize: 25, nullable: true
		siteId maxSize: 8, blank:false
		companyId maxSize: 8, blank:false
		vendor maxSize: 12, nullable: true
		manufacturer maxSize: 12, nullable: true
		purchasePrice scale: 2, nullable: true
		replaceCost scale: 2, nullable: true
		grossCost scale: 2, nullable: true
		toolRate scale: 2, nullable: true
		itemId maxSize: 30, nullable: true
		installDate nullable: true
		expiryDate nullable: true
		updatedBy maxSize: 30, nullable: true, display: false
		lastUpdated display: false
		isActive display: false
		isDisabled display: false
		dateCreated unique: true, display: false
		fAddressCode maxSize: 12, nullable: true, display: false
		itemSetId maxSize: 8, nullable: true, display: false
	}
	static mapping = {
		id column: 'assetuid'
	}
	String toString() {
		assetNumber + " "+ siteId
	}
}
