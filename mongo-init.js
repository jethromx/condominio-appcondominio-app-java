
db.createUser({
    user: "ie-user",
    pwd: "pass",
    roles: [
      {
        role: "readWrite",
        db: "intelligent-extract"
      }
    ]
  });
  
  dbie = db.getSiblingDB('intelligent-extract');
  
  dbie.createCollection('quotas');
  dbie.createCollection('potions');
  dbie.createCollection('providers');
  dbie.createCollection('ecs.intelligent-extract-play');
  dbie.createCollection('ecs.intelligent-extract.internal');
  dbie.createCollection('ecs.intelligent-extract.test');
  dbie.createCollection('user.e050898');
  
  db.providers.insert([{
    "_id": "dvs",
    "availableInputs": [
      "jpg"
    ],
    "countries": [
      "global"
    ], 
    "specs":[
      {
        "key":"validationType", 
        "type": "STRING",
        "help": "WITH_FLASH | WITHOUT_FLASH"
      },
      {
        "key":"documentType", 
        "type": "STRING",
        "help": "ES | MX"
      }
    ],
    "envs": [
      {
        "key": "lab-01",
        "specs": [
          {
            "key": "url",
            "value": "https://dvs-work.work-01.ether.igrupobbva/v1/ns/%s/verifications/",
            "type": "URL",
            "help": "Path where the model is deployed"
          }
        ]
      },
      {
        "key": "work-01",
        "specs": [
          {
            "key": "url",
            "value": "https://dvs-work.work-01.ether.igrupobbva/v1/ns/%s/verifications/",
            "type": "URL",
            "help": "Path where the model is deployed"
          }
        ]
      },
      {
        "key": "work-02",
        "specs": [
          {
            "key": "url",
            "value": "https://dvs-work.work-02.ether.igrupobbva/v1/ns/%s/verifications/",
            "type": "URL",
            "help": "Path where the model is deployed"
          }
        ]
      },
      {
        "key": "live-01",
        "specs": [
          {
            "key": "url",
            "value": "https://dvs-live.live-01.ether.igrupobbva/v1/ns/%s/verifications/",
            "type": "URL",
            "help": "Path where the model is deployed"
          }
        ]
      },
      {
        "key": "live-02",
        "specs": [
          {
            "key": "url",
            "value": "https://dvs-live.live-02.ether.igrupobbva/v1/ns/%s/verifications/",
            "type": "URL",
            "help": "Path where the model is deployed"
          }
        ]
      }
    ],
    "authentication":[
      {
        "key":"type", 
        "value":"cert"
      }
    ]
  }, 
  {
    "_id": "innerModel",
    "availableInputs": [
      "jpg", "xml", "cmc7", "qr"
    ],
    "countries": [
      "global"
    ], 
   
   "specs":[
     {
       "help":"Name of the environment variable that contains the url of the model",
       "type":"ENV",
       "key":"URL"
     },
     {
       "help":"Service route",
       "type":"STRING",
       "key":"PATH"
     }
   ],
    "authentication":[
      {
        "key":"type", 
        "value":"cert"
      }
    ]
  }]);
  
  db.potions.insert([
    {
      "documentType": "CO-FO-00365",
      "provider": "innerModel",
      "namespace": "adminNs",
      "prepare": {
        "grayscale": false,
        "clean": false,
        "deskew": false,
        "cropping": false
      },
      "kind": "MONO_MODEL", 
      "mapperDatapoints":[
        {
          "id": "gf_monitor_imei_id_1",
          "path": "$.datapoints[?(@.name == 'imei1')].value",
          "group": null,
          "alias":"gf_monitor_imei_id",
          "datagroup": "imei1"
        },
        {
          "id": "gf_monitor_imei_id_2",
          "path": "$.datapoints[?(@.name == 'imei2')].value",
          "group": null,
          "alias":"gf_monitor_imei_id",
          "datagroup": "imei2"
        }
      ], 
      "specs":[
        {
         "value":"HOST_IMEI_MODEL",
         "type":"ENV",
         "key":"URL"
       },
        {
         "value":"%s/v1/ns/%s/imei",
         "type":"STRING",
         "key":"PATH"
       }
      ],
      "inputs":["xml"]
    },{
      "documentType": "CO-PAYROLL-00000",
      "provider": "innerModel",
      "prepare": {
        "grayscale": true,
        "clean": true,
        "deskew": true,
        "cropping": true
      },
      "kind": "MONO_MODEL", 
      "mapperDatapoints":[
        {
          "id": "url",
          "path": "$.datapoints[?(@.name == 'qrvalue')].value",
          "group": null,
          "alias":"url",
          "datagroup": ""
        }
      ], 
      "specs":[
        {
         "value":"HOST_PAYROLL_MODEL",
         "type":"ENV",
         "key":"URL"
       },
        {
         "value":"%s/v1/ns/%s/payroll",
         "type":"STRING",
         "key":"PATH"
       }
      ],
      "inputs":["qr"]
    },{
      "documentType": "PE-EA-00109",
      "namespace": "adminNs",
      "provider": "innerModel",
      "prepare": {
        "grayscale": true,
        "clean": true,
        "deskew": true,
        "cropping": true
      },
      "kind": "MONO_MODEL", 
      "mapperDatapoints":[
        {
            "id": "gf_beneficiary_name",
            "path": "$.datapoints[?(@.name == 'gf_beneficiary_name')].value",
            "group": null,
            "alias":"gf_beneficiary_name",
            "datagroup": null
        },
        {
            "id": "gf_check_letter_amount",
            "path": "$.datapoints[?(@.name == 'gf_check_letter_amount')].value",
            "group": null,
            "alias":"gf_check_letter_amount",
            "datagroup": null
        },
        {
            "id": "gf_check_amount",
            "path": "$.datapoints[?(@.name == 'gf_check_digits_amount')].value",
            "group": null,
            "alias":"gf_check_amount",
            "datagroup": null
        },
        {
          "id": "gf_account_id",
          "path": "$.datapoints[?(@.name == 'gf_account_id')].value",
          "group": null,
          "alias":"gf_account_id",
          "datagroup": null
        },
        {
          "id": "gf_check_num_id",
          "path": "$.datapoints[?(@.name == 'gf_check_num_id')].value",
          "group": null,
          "alias":"gf_check_num_id",
          "datagroup": null
        },
        {
          "id": "check_security_id",
          "path": "$.datapoints[?(@.name == 'check_security_id')].value",
          "group": null,
          "alias":"check_security_id",
          "datagroup": null
        },
        {
          "id": "check_transit_id",
          "path": "$.datapoints[?(@.name == 'check_transit_id')].value",
          "group": null,
          "alias":"check_transit_id",
          "datagroup": null
        },
        {
          "id": "check_band_id",
          "path": "$.datapoints[?(@.name == 'check_band_id')].value",
          "group": null,
          "alias":"check_band_id",
          "datagroup": null
        },
        {
          "id": "check_folio_id",
          "path": "$.datapoints[?(@.name == 'check_folio_id')].value",
          "group": null,
          "alias":"check_folio_id",
          "datagroup": null
        },
        {
          "id": "gf_zipcode_id",
          "path": "$.datapoints[?(@.name == 'gf_zipcode_id')].value",
          "group": null,
          "alias":"gf_zipcode_id",
          "datagroup": null
        },
        {
          "id": "check_entity_id",
          "path": "$.datapoints[?(@.name == 'check_entity_id')].value",
          "group": null,
          "alias":"check_entity_id",
          "datagroup": null
        },
        {
          "id": "check_issuer_branch_id",
          "path": "$.datapoints[?(@.name == 'check_issuer_branch_id')].value",
          "group": null,
          "alias":"check_issuer_branch_id",
          "datagroup": null
        }
      ], 
      "specs":[
        {
         "value":"HOST_CHECKS_MODEL",
         "type":"ENV",
         "key":"URL"
       },
        {
         "value":"%s/v1/ns/%s/checks",
         "type":"STRING",
         "key":"PATH"
       }
      ],
      "inputs":["jpg", "xml", "cmc7"]
    },{
      "_id" : "b5907383-8a34-41fc-bfb8-b76e003a2a36",
      "documentType" : "PE-ID-00001",
      "provider" : "innerModel",
      "mapperDatapoints" : [ 
          {
              "_id" : "gf_personal_id",
              "path" : "$.datapoints[?(@.name == 'dm_id_number')].value",
              "alias" : "gf_personal_id"
          }, 
          {
              "_id" : "curp_id",
              "path" : "$.datapoints[?(@.name == 'dm_national_id_number')].value",
              "alias" : "curp_id"
          }, 
          {
              "_id" : "gf_code_ocr",
              "path" : "$.datapoints[?(@.name == 'dm_ocr')].value",
              "alias" : "gf_code_ocr"
          }, 
          {
              "_id" : "gf_code_id_Credential",
              "path" : "$.datapoints[?(@.name == 'dm_cic')].value",
              "alias" : "gf_code_id_Credential"
          }, 
          {
              "_id" : "gf_birth_date",
              "path" : "$.datapoints[?(@.name == 'dm_birth_date')].value",
              "alias" : "gf_birth_date"
          }, 
          {
              "_id" : "document_validity_date",
              "path" : "$.datapoints[?(@.name == 'dm_due_date')].value",
              "alias" : "document_validity_date"
          }, 
          {
              "_id" : "gf_expedition_document_date",
              "path" : "$.datapoints[?(@.name == 'gf_expedition_document_date')].value",
              "alias" : "gf_expedition_document_date"
          }, 
          {
              "_id" : "gf_first_name",
              "path" : "$.datapoints[?(@.name == 'dm_first_name')].value",
              "alias" : "gf_first_name"
          }, 
          {
              "_id" : "dm_middle_name",
              "path" : "$.datapoints[?(@.name == 'dm_middle_name')].value",
              "alias" : "dm_middle_name"
          }, 
          {
              "_id" : "gf_last_name",
              "path" : "$.datapoints[?(@.name == 'dm_last_name1')].value",
              "alias" : "gf_last_name"
          }, 
          {
              "_id" : "gf_second_last_name",
              "path" : "$.datapoints[?(@.name == 'dm_last_name2')].value",
              "alias" : "gf_second_last_name"
          }, 
          {
              "_id" : "gf_full_name",
              "path" : "$.datapoints[?(@.name == 'dm_name')].value",
              "alias" : "gf_full_name"
          }, 
          {
              "_id" : "gf_gender_type",
              "path" : "$.datapoints[?(@.name == 'dm_sex')].value",
              "alias" : "gf_gender_type"
          }, 
          {
              "_id" : "gf_address_desc",
              "path" : "$.datapoints[?(@.name == 'direction')].value",
              "alias" : "gf_address_desc"
          }
      ],
      "prepare" : {
          "grayscale" : true,
          "clean" : true,
          "rotation" : -90,
          "cropping" : true,
          "deskew" : false
      },
      "kind" : "MULTI_MODEL",
      "namespace" : "adminNs",
      "specs" : [ 
          {
              "type" : "ENV",
              "key" : "URL",
              "value" : "HOST_IDEN_DOC_MODEL"
          }, 
          {
              "type" : "STRING",
              "key" : "PATH",
              "value" : "%s/v1/ns/%s/INEParser"
          }
      ],
      "inputs" : [ 
          "jpg", 
          "xml", 
          "hocr"
      ],
      "etherCreatedAt" : "2022-01-21T08:45:38Z",
      "etherid" : "b5907383-8a34-41fc-bfb8-b76e003a2a36",
      "etherUpdatedAt" : "2022-01-26T16:25:54Z",
      "_class" : "com.bbva.seiri.cauldron.dto.PotionDto"
  },{
    "documentType": "PE-ID-00027",
    "namespace": "adminNs",
    "provider": "dvs",
    "prepare": {
      "grayscale": true,
      "clean": true,
      "deskew": true,
      "cropping": true
    },
    "kind": "MONO_MODEL", 
    "mapperDatapoints":[
      {
        "id": "gf_address_desc",
        "path": "$.concat($.documentExtractedData.permanentAddress,\" \",$.documentExtractedData.permanentAddressLocality,\" \",$.documentExtractedData.permanentAddressMunicipality)",
        "group": null,
        "alias":"gf_address_desc",
        "datagroup": null
      },
      {
        "id": "gf_personal_id",
        "path": "$.documentExtractedData.documentNumber",
        "group": null,
        "alias":"gf_personal_id",
        "datagroup": null
      },
      {
        "id": "gf_birth_date",
        "path": "$.documentExtractedData.birthDate",
        "group": null,
        "alias":"gf_birth_date",
        "datagroup": null
      },
      {
        "id": "document_validity_date",
        "path": "$.documentExtractedData.expirationDate",
        "group": null,
        "alias":"document_validity_date",
        "datagroup": null
      },
      {
        "id": "gf_first_name",
        "path": "$.documentExtractedData.firstName",
        "group": null,
        "alias":"gf_first_name",
        "datagroup": null
      },
      {
        "id": "gf_last_name",
        "path": "$.documentExtractedData.lastName",
        "group": null,
        "alias":"gf_last_name",
        "datagroup": null
      },
      {
        "id": "gf_second_last_name",
        "path": "$.documentExtractedData.secondLastName",
        "group": null,
        "alias":"gf_second_last_name",
        "datagroup": null
      },
      {
        "id": "gf_full_name",
        "path": "$.documentExtractedData.fullName",
        "group": null,
        "alias":"gf_full_name",
        "datagroup": null
      },
      {
        "id": "gf_gender_type",
        "path": "$.documentExtractedData.gender.name",
        "group": null,
        "alias":"gf_gender_type",
        "datagroup": null
      }
    ], 
    "specs":[
      {
        "key":"validationType", 
        "type": "STRING",
        "value": "WITHOUT_FLASH"
      },
      {
        "key":"documentType", 
        "type": "STRING",
        "value": "ES"
      }
    ],
    "inputs":["jpg"]
  },{
    "documentType": "PE-RE-00017",
    "provider": "innerModel",
    "prepare": {
        "grayscale": true,
        "clean": true,
        "deskew": true,
        "cropping": true
    },
    "kind": "MONO_MODEL",
    "mapperDatapoints": [
        {
            "id": "gf_address_desc",
            "path": "$.datapoints[?(@.name == 'gf_address_desc')].value",
            "alias": "gf_address_desc",
            "type": null,
            "group": null,
            "datagroup": "",
            "accuracy": null,
            "location": null
        },
        {
            "id": "gf_road_name",
            "path": "$.datapoints[?(@.name == 'gf_road_name')].value",
            "alias": "gf_road_name",
            "type": null,
            "group": null,
            "datagroup": "",
            "accuracy": null,
            "location": null
        },
        {
            "id": "gf_street_number",
            "path": "$.datapoints[?(@.name == 'gf_street_number')].value",
            "alias": "gf_street_number",
            "type": null,
            "group": null,
            "datagroup": "",
            "accuracy": null,
            "location": null
        },
        {
            "id": "between_street_name",
            "path": "$.datapoints[?(@.name == 'between_street_name')].value",
            "alias": "between_street_name",
            "type": null,
            "group": null,
            "datagroup": "",
            "accuracy": null,
            "location": null
        },
        {
            "id": "gf_address_indoor_number",
            "path": "$.datapoints[?(@.name == 'indoor_number')].value",
            "alias": "gf_address_indoor_number",
            "type": null,
            "group": null,
            "datagroup": "",
            "accuracy": null,
            "location": null
        },
        {
            "id": "suburb_name",
            "path": "$.datapoints[?(@.name == 'suburb_name')].value",
            "alias": "suburb_name",
            "type": null,
            "group": null,
            "datagroup": "",
            "accuracy": null,
            "location": null
        },
        {
            "id": "gf_town_name",
            "path": "$.datapoints[?(@.name == 'gf_town_name')].value",
            "alias": "gf_town_name",
            "type": null,
            "group": null,
            "datagroup": "",
            "accuracy": null,
            "location": null
        },
        {
            "id": "gf_zipcode_id",
            "path": "$.datapoints[?(@.name == 'gf_zipcode_id')].value",
            "alias": "gf_zipcode_id",
            "type": null,
            "group": null,
            "datagroup": "",
            "accuracy": null,
            "location": null
        },
        {
            "id": "gf_geographical_group_name",
            "path": "$.datapoints[?(@.name == 'gf_geographical_group_name')].value",
            "alias": "gf_geographical_group_name",
            "type": null,
            "group": null,
            "datagroup": "",
            "accuracy": null,
            "location": null
        },
        {
            "id": "gf_expedition_document_date",
            "path": "$.datapoints[?(@.name == 'gf_expedition_document_date')].value",
            "alias": "gf_expedition_document_date",
            "type": null,
            "group": null,
            "datagroup": "",
            "accuracy": null,
            "location": null
        }
    ],
    "specs": [
        {
            "value": "HOST_ADDRESS_MODEL",
            "type": "ENV",
            "key": "URL"
        },
        {
            "value": "%s/v1/ns/%s/address/extract",
            "type": "STRING",
            "key": "PATH"
        }
    ],
    "inputs": [
        "xml"
    ]
  },{
    "documentType": "PE-ID-00010",
    "provider": "dvs",
    "namespace" : "adminNs",
    "prepare": {
      "grayscale": false,
      "clean": false,
      "deskew": false,
      "cropping": false
    },
    "kind": "MONO_MODEL", 
    "mapperDatapoints":[
      {
        "id": "gf_personal_id",
        "path": "$.documentExtractedData.optionalData.identificationNumber",
        "alias":"gf_personal_id",
        "type": "STRING"
      },
      {
        "id": "gf_birth_date",
        "path": "$.documentExtractedData.birthDate",
        "alias":"gf_birth_date",
        "type": "DATE"
      },
      {
        "id": "document_validity_date",
        "path": "$.documentExtractedData.expirationDate",
        "type": "DATE",
        "alias":"document_validity_date"
      },
      {
        "id": "gf_first_name",
        "path": "$.documentExtractedData.firstName",
        "type": "STRING",
        "alias":"gf_first_name"
      },
      {
        "id": "gf_last_name",
        "path": "$.documentExtractedData.lastName",
        "type": "STRING",
        "alias":"gf_last_name"
      },
      {
        "id": "gf_full_name",
        "path": "$.documentExtractedData.fullName",
        "type": "STRING",
        "alias":"gf_full_name"
      },
      {
        "id": "gf_gender_type",
        "path": "$.documentExtractedData.gender.name",
        "type": "STRING",
        "alias":"gf_gender_type"
      },
      {
        "id": "gf_country_nationality_id",
        "path": "$.documentExtractedData.optionalData.nationality",
        "type": "STRING",
        "alias":"gf_country_nationality_id"
      }
    ], 
    "specs":[
      {
        "key":"validationType", 
        "type": "STRING",
        "value": "WITHOUT_FLASH"
      },
      {
        "key":"documentType", 
        "type": "STRING",
        "value": "XX_PASSPORT_YYYY"
      }
    ],
    "inputs":["jpg"],
    "objectRecognition":{
      "faces":true,
      "signatures":true
    }
  }, 
  {
    "documentType" : "CO-FO-00409",
    "provider" : "innerModel",
    "mapperDatapoints" : [ 
        {
            "_id" : "dm_first_name",
            "path" : "$.datapoints[?(@.name == 'dm_first_name')].value",
            "alias" : "dm_first_name",
            "datagroup" : ""
        }, 
        {
            "_id" : "dm_middle_name",
            "path" : "$.datapoints[?(@.name == 'dm_middle_name')].value",
            "alias" : "dm_middle_name",
            "datagroup" : ""
        }, 
        {
            "_id" : "dm_last_name1",
            "path" : "$.datapoints[?(@.name == 'dm_last_name1')].value",
            "alias" : "dm_last_name1",
            "datagroup" : ""
        }, 
        {
            "_id" : "dm_last_name2",
            "path" : "$.datapoints[?(@.name == 'dm_last_name2')].value",
            "alias" : "dm_last_name2",
            "datagroup" : ""
        }, 
        {
            "_id" : "dm_state",
            "path" : "$.datapoints[?(@.name == 'dm_state')].value",
            "alias" : "dm_state",
            "datagroup" : ""
        }
    ],
    "prepare" : {
        "grayscale" : true,
        "clean" : true,
        "rotation" : 0,
        "cropping" : true,
        "deskew" : true
    },
    "kind" : "MULTI_MODEL",
    "namespace" : "adminNs",
    "specs" : [ 
        {
            "type" : "ENV",
            "key" : "URL",
            "value" : "HOST_BUROSHEET_MODEL"
        }, 
        {
            "type" : "STRING",
            "key" : "PATH",
            "value" : "%s/v1/ns/%s/buroSheetExtractor"
        }
    ],
    "inputs" : [ 
        "xml", 
        "hocr"
    ],
    "objectRecognition" : {
        "faces" : true,
        "signatures" : true
    }
  }
  
  
    
  ])
  
  db.quotas.insert([{
    "_id": "ecs.intelligent-extract-play",
    "maxExtractionsMonth": 100,
    "maxExtractionsStock": 20
  },
  {
    "_id": "ecs.intelligent-extract.internal",
    "maxExtractionsMonth": 100,
    "maxExtractionsStock": 20
  },
  {
    "_id": "ecs.intelligent-extract.test",
    "maxExtractionsMonth": 300,
    "maxExtractionsStock": 20
  },
  {
    "_id": "user.e050898",
    "maxExtractionsMonth": 100,
    "maxExtractionsStock": 20
  }])