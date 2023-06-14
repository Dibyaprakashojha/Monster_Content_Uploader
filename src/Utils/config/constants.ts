export const otmmServicesConstants = {
  CONTENT_TYPE: {
    URLENCODED: 'application/x-www-form-urlencoded',
    MULTIPARTFORMDATA: 'multipart/form-data',
    JSONDATA: 'application/json',
    UNDEFINED: undefined,
  },

  // Base folder Configuration
  defaultFolderModel: 'ARTESIA.MODEL.DEFAULT',
  checkinUrl: 'jobs/checkins',
  importJobUrl: 'jobs/checkins',
  renditionsUrl: 'renditions',

  textAssetSearchUrl: '/search/text',

  copyAssetToFolder: {
    originalParentMetadataField: 'SALESFORCE.METADATA.ORIGINALPARENTFOLDERID',
    updateMetadataSectionId: 'METADATA_MISC_EDIT_SECTION',
  },

  preferenceID: {
    GALLERY_VIEW: 'ARTESIA.PREFERENCE.GALLERYVIEW.DISPLAYED_FIELDS',
  },
};
