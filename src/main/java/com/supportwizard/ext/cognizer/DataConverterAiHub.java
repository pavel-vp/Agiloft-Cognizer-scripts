//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.supportwizard.ext.cognizer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supportwizard.ext.cognizer.model.AiHubLicense;
import com.supportwizard.swlicenses.data.LicenseData;
import com.supportwizard.swlicenses.exceptions.InvalidLicenseActionException;
import com.supportwizard.utils.Base64;
import com.supportwizard.utils.DateConverter;
import com.supportwizard.utils.DateConverterException;
import java.io.IOException;
import java.util.Date;
import java.util.StringTokenizer;

public class DataConverterAiHub {
    public DataConverterAiHub() {
    }

    public static LicenseData getLicenseFromString(String licenseString) throws InvalidLicenseActionException {
        LicenseData ld = new LicenseData();
        ld.setUnlimited(Boolean.FALSE);

        try {
            StringTokenizer tk = new StringTokenizer(licenseString, "/");

            while(tk.hasMoreTokens()) {
                String token = tk.nextToken();
                String type = token.substring(0, 1);
                String value = token.substring(1);
                if ("v".equalsIgnoreCase(type)) {
                    ld.setVersion(value);
                } else if ("h".equalsIgnoreCase(type)) {
                    ld.setHost(value);
                } else if ("k".equalsIgnoreCase(type)) {
                    ld.setKnowledgebase(value);
                } else if ("i".equalsIgnoreCase(type)) {
                    ld.setIp(value);
                } else if ("a".equalsIgnoreCase(type)) {
                    ld.setActivityPeriod(Integer.valueOf(value));
                } else {
                    Date tmp;
                    if ("b".equalsIgnoreCase(type)) {
                        tmp = DateConverter.getDate(value);
                        ld.setStartDate(tmp);
                    } else if ("e".equalsIgnoreCase(type)) {
                        tmp = DateConverter.getDate(value);
                        ld.setEndDate(tmp);
                    } else {
                        if ("l".equalsIgnoreCase(type)) {
                            ld.setLicenseKey(licenseString.substring(licenseString.indexOf("/" + type) + 2));
                            break;
                        }

                        if ("s".equalsIgnoreCase(type)) {
                            ld.setLicenseType("staff");
                            if ("u".equalsIgnoreCase(value)) {
                                ld.setUnlimited(Boolean.TRUE);
                                ld.setQuantity(new Integer(0));
                            } else {
                                ld.setQuantity(Integer.valueOf(value));
                            }
                        } else if ("u".equalsIgnoreCase(type)) {
                            ld.setLicenseType("user");
                            if ("u".equalsIgnoreCase(value)) {
                                ld.setUnlimited(Boolean.TRUE);
                                ld.setQuantity(new Integer(0));
                            } else {
                                ld.setUnlimited(Boolean.FALSE);
                                ld.setQuantity(Integer.valueOf(value));
                            }
                        } else if ("o".equalsIgnoreCase(type)) {
                            ld.setLicenseType("outbound");
                            if ("u".equalsIgnoreCase(value)) {
                                ld.setUnlimited(Boolean.TRUE);
                                ld.setQuantity(new Integer(0));
                            } else {
                                ld.setUnlimited(Boolean.FALSE);
                                ld.setQuantity(Integer.valueOf(value));
                            }
                        } else if ("p".equalsIgnoreCase(type)) {
                            ld.setLicenseType("staff_per_kb");
                            if ("u".equalsIgnoreCase(value)) {
                                ld.setUnlimited(Boolean.TRUE);
                                ld.setQuantity(new Integer(0));
                            } else {
                                ld.setUnlimited(Boolean.FALSE);
                                ld.setQuantity(Integer.valueOf(value));
                            }
                        } else if ("r".equalsIgnoreCase(type)) {
                            ld.setLicenseType("user_per_kb");
                            if ("u".equalsIgnoreCase(value)) {
                                ld.setUnlimited(Boolean.TRUE);
                                ld.setQuantity(new Integer(0));
                            } else {
                                ld.setUnlimited(Boolean.FALSE);
                                ld.setQuantity(Integer.valueOf(value));
                            }
                        } else if ("x".equalsIgnoreCase(type)) {
                            ld.setLicenseType("free_staff");
                            if ("u".equalsIgnoreCase(value)) {
                                ld.setUnlimited(Boolean.TRUE);
                                ld.setQuantity(new Integer(0));
                            } else {
                                ld.setUnlimited(Boolean.FALSE);
                                ld.setQuantity(Integer.valueOf(value));
                            }
                        } else if ("t".equalsIgnoreCase(type)) {
                            ld.setLicenseType("language");
                            ld.setLanguages(value);
                        } else if ("f".equalsIgnoreCase(type)) {
                            ld.setLicenseType("free_language");
                            ld.setLanguages(value);
                        } else if ("n".equalsIgnoreCase(type)) {
                            ld.setLanguages(value);
                        } else if ("w".equalsIgnoreCase(type)) {
                            ld.setLicenseType("api");
                            ld.setQuantity(Integer.valueOf(value));
                        } else if ("y".equalsIgnoreCase(type)) {
                            ld.setLicenseType("background");
                            ld.setQuantity(Integer.valueOf(value) * 60 * 1000);
                        } else if ("z".equalsIgnoreCase(type)) {
                            if ("z".equalsIgnoreCase(value)) {
                                ld.setLicenseType("enterprise_ext");
                            } else {
                                ld.setLicenseType("enterprise");
                            }
                        } else if ("g".equalsIgnoreCase(type)) {
                            ld.setLicenseType("vendor");
                            ld.setLanguages(value);
                        } else if ("d".equalsIgnoreCase(type)) {
                            ld.setLicenseType("integration_hub");
                        } else if ("c".equalsIgnoreCase(type)) {
                            ld.setLicenseType("ai_hub");
//                            ObjectMapper objectMapper = new ObjectMapper();

//                            try {
//                                AiHubLicense aiHubLicense = (AiHubLicense)objectMapper.readValue(Base64.decode(value.toCharArray()), AiHubLicense.class);
//                                ld.setQuantity(Math.toIntExact(aiHubLicense.getNoOfSubscription()));
                                ld.setLanguages(value);
//                            } catch (IOException var8) {
//                                throw new InvalidLicenseActionException(var8, "licenses.parse.error.ai_hub", new Object[]{var8});
//                            }
                        }
                    }
                }
            }

            ld.setName(ld.getHost() + ":" + ld.getIp() + ":" + ld.getLicenseType() + ":" + ld.getKnowledgebase());
            return ld;
        } catch (NumberFormatException var9) {
            throw new InvalidLicenseActionException(var9, "licenses.parse.error.numeric", new Object[]{var9});
        } catch (DateConverterException var10) {
            throw new InvalidLicenseActionException(var10, "licenses.parse.error.date", new Object[]{var10});
        }
    }
}
