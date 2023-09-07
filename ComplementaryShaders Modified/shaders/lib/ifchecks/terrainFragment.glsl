if (mat > 100.5 && mat < 10000.0) {
    if (mat < 152.5) {
        if (mat < 132.5) {
            if (mat < 124.5) {
                if (material == 120.0) { // Redstone Stuff
                    #ifndef WORLD_CURVATURE
                        float comPos = fract(worldPos.y + cameraPosition.y);
                    #else
                        float comPos = fract(oldPosition.y + cameraPosition.y);
                    #endif
                    if (comPos > 0.18) emissive = float((albedo.r > 0.65 && albedo.r > albedo.b * 1.0) || albedo.b > 0.99);
                    else emissive = float(albedo.r > albedo.b * 3.0 && albedo.r > 0.5) * 0.125;
                    emissive *= max(0.65 - 0.3 * dot(albedo.rgb, vec3(1.0, 1.0, 0.0)), 0.0);
                    if (specB > 900.0) { // Observer
                        emissive *= float(albedo.r > albedo.g * 1.5);
                    }
                }
                else if (material == 124.0) { // Warped Stem+
                    #ifdef EMISSIVE_NETHER_STEMS
                        float core = float(albedo.r < 0.1);
                        float edge = float(albedo.b > 0.35 && albedo.b < 0.401 && core == 0.0);
                        emissive = core * 0.195 + 0.035 * edge;
                    #endif
                }
            } else {
                if (material == 128.0) { // Crimson Stem+
                    #ifdef EMISSIVE_NETHER_STEMS
                        emissive = float(albedo.b < 0.16);
                        emissive = min(pow2(lAlbedoP * lAlbedoP) * emissive * 3.0, 0.3);
                    #endif
                }
                else if (material == 130.0) { // Sculk++
                    emissive *= max((albedo.b - albedo.r) - 0.1, 0.0) * 0.5
                              + 100.0 * max(albedo.g - albedo.b, 0.0) * float(albedo.r < albedo.b - 0.1)
                              ;
                }
                else if (material == 132.0) { // Command Blocks
                    #ifndef WORLD_CURVATURE
                        vec3 comPos = fract(worldPos.xyz + cameraPosition.xyz);
                    #else
                        vec3 comPos = fract(oldPosition.xyz + cameraPosition.xyz);
                    #endif
                    comPos = abs(comPos - vec3(0.5));
                    float comPosM = min(max(comPos.x, comPos.y), min(max(comPos.x, comPos.z), max(comPos.y, comPos.z)));
                    emissive = 0.0;
                    if (comPosM < 0.1882) { // Command Block Center
                        vec3 dif = vec3(albedo.r - albedo.b, albedo.r - albedo.g, albedo.b - albedo.g);
                        dif = abs(dif);
                        emissive = float(max(dif.r, max(dif.g, dif.b)) > 0.1) * 25.0;
                        emissive *= float(albedo.r > 0.44 || albedo.g > 0.29);

                        if (CheckForColor(albedo.rgb, vec3(207, 166, 139)) // Fix for Iris' precision
                        ||  CheckForColor(albedo.rgb, vec3(201, 143, 107))
                        ||  CheckForColor(albedo.rgb, vec3(161, 195, 180))
                        ||  CheckForColor(albedo.rgb, vec3(131, 181, 145))
                        ||  CheckForColor(albedo.rgb, vec3(155, 139, 207))
                        ||  CheckForColor(albedo.rgb, vec3(135, 121, 181))) emissive = 0.0;
                        
                        #ifdef ALTERNATIVE_COMMAND_BLOCK
                            if (emissive > 0.01) {
                                albedo.rgb *= vec3(0.88, 1.32, 1.9);
                                albedo.g = sqrt1(albedo.g) * 0.6;
                                albedo.rgb *= albedo.rgb * 2.0;
                            }
                        #endif
                    }
                    vec3 dif = abs(vec3(albedo.r - albedo.g, albedo.g - albedo.b, albedo.r - albedo.b));
                    float maxDif = max(dif.r, max(dif.g, dif.b));
                    smoothness = 0.38;
                    if (maxDif < 0.05) smoothness = 0.6;
                    metalness = 1.0;
                }
            }
        } else {
            if (mat < 144.5) {
                if (material == 136.0) { // Snowy Grass Block
                    if (lAlbedoP > 1.0) smoothness = lAlbedoP * lAlbedoP * 0.165;
                    else metalness = 0.003;
                }
                else if (material == 140.0) { // Dragon Egg, Spawner
                    emissive = float(albedo.r + albedo.b > albedo.g * 30.0 && lAlbedoP < 0.6);
                    emissive *= 8.0 + float(lAlbedoP < 0.4) * 100.0;
                    if (albedo.b + albedo.g > albedo.r * 2.0 && lAlbedoP > 0.2) { // Spawner Metal
                        smoothness = 0.385;
                        metalness = 0.8;
                    }
                    if (max(abs(albedo.r - albedo.b), abs(albedo.g - albedo.r)) < 0.01) { // Dragon Egg Subtle Emission
                        emissive = 2.5 * float(lAlbedoP < 0.2);
                    }
                }
                else if (material == 144.0) // Furnaces Lit
                    emissive = 0.75 * float(albedo.r * albedo.r > albedo.b * 4.0 || (albedo.r > 0.9 && (albedo.r > albedo.b || albedo.r > 0.99)));
            } else {
                if (material == 148.0) {// Torch, Soul Torch
					if (texX > 0.437 && texX < 0.5625 && texY > 0.37 && texY < 0.505) {
						emissive = 1.4 - 1.05;
					}
					else {
						emissive = 0.0;
					}
                    //emissive = float(albedo.r > 0.9 || albedo.b > 0.65) * (1.4 - albedo.b * 1.05);
                    /* {
                        #ifndef WORLD_CURVATURE
                            vec3 comPos = fract(worldPos.xyz + cameraPosition.xyz);
                        #else
                            vec3 comPos = fract(oldPosition.xyz + cameraPosition.xyz);
                        #endif
                        comPos = abs(comPos - vec3(0.5));
                        float comPosM = max(max(comPos.x, comPos.y), comPos.z);
                        emissive = clamp(1.0 - comPosM * 2.0, 0.0, 1.0);
                        if (emissive > 0.001) {
                            emissive *= emissive;
                            emissive *= emissive;
                            emissive *= pow(lAlbedoP * 0.7, 4.0) * 0.75;
                            emissive = min(emissive, 0.15);
                        } else emissive = 0.0;
                        lightmap.x = min(emissive * 10.0 + 0.6, 1.05);
                        albedo.rgb = pow(albedo.rgb, vec3(1.4 - lightmap.x));
                    } */
				}
                else if (material == 152.0) { // Obsidian++
                    smoothness = max(smoothness, 0.375);
                    if (specB > 0.5) { // Crying Obsidian, Respawn Anchor
                        emissive = (albedo.b - albedo.r) * albedo.r * 6.0;
                        emissive *= emissive * emissive;
                        emissive = clamp(emissive, 0.05, 1.0);
                        if (lAlbedoP > 1.6 || albedo.r > albedo.b * 1.7) emissive = 1.0;
                    } else {
                        if (lAlbedoP > 0.75) { // Enchanting Table Diamond
                            f0 = smoothness;
                            smoothness = 0.9 - f0 * 0.1;
                            metalness = 0.0;
                        }
                        if (albedo.r > albedo.g + albedo.b) { // Enchanting Table Cloth
                            smoothness = max(smoothness - 0.45, 0.0);
                            metalness = 0.0;
                        }
                    }
                }
            }
        }
    } else {
        if (mat < 170.5) {
            if (mat < 162.5) {
                if (material == 156.0) { // Campfires, Powered Lever
                    if (albedo.g + albedo.b > albedo.r * 2.3 && albedo.g > 0.38 && albedo.g > albedo.b * 0.9) emissive = 0.09;
                    if (albedo.r > albedo.b * 3.0 || albedo.r > 0.8) emissive = 0.65;
                    emissive *= max(1.0 - albedo.b + albedo.r, 0.0);
                    emissive *= lAlbedoP;
                }
                else if (material == 160.0) { // Cauldron, Hopper, Anvils
                    if (color.r < 0.99) { // Cauldron Water
                        cauldron = 1.0, smoothness = 1.0, metalness = 0.0;
                        skymapMod = lmCoord.y * 0.475 + 0.515;
                        #ifdef REFLECTION_RAIN
                            noRain = 1.0;
                        #endif
                        #if WATER_TYPE == 0
                            albedo.rgb = waterColor.rgb;
                        #elif WATER_TYPE == 1
                            albedo.rgb = pow(albedo.rgb, vec3(1.3));
                        #else
                            albedo.rgb = vec3(0.4, 0.5, 0.4) * (pow(albedo.rgb, vec3(2.8)) + 4 * waterColor.rgb * pow(albedo.r, 1.8)
                                                        + 16 * waterColor.rgb * pow(albedo.g, 1.8) + 4 * waterColor.rgb * pow(albedo.b, 1.8));
                            albedo.rgb = pow(albedo.rgb * 1.5, vec3(0.5, 0.6, 0.5)) * 0.6;
                            albedo.rgb *= 1 + length(albedo.rgb) * pow(WATER_OPACITY, 32.0) * 2.0;
                        #endif
                        #ifdef NORMAL_MAPPING
                            vec2 cauldronCoord1 = texCoord + fract(frametime * 0.003);
                            float cauldronNoise1 = texture2D(noisetex, cauldronCoord1).r;
                            vec2 cauldronCoord2 = texCoord - fract(frametime * 0.003);
                            float cauldronNoise2 = texture2D(noisetex, cauldronCoord2).r;
                            float waveFactor = 0.027 + 0.065 * lightmap.y;
                            normalMap.xy += (0.5 * waveFactor) * (cauldronNoise1 * cauldronNoise2 - 0.3);
                            albedo.rgb *= (1.0 - waveFactor * 0.5) + waveFactor * cauldronNoise1 * cauldronNoise2;
                        #endif
                    }
                    #if MC_VERSION >= 11700
                    else if (albedo.r * 1.5 > albedo.g + albedo.b) { // Cauldron Lava
                        metalness = 0.0;
                        smoothness = 0.0;

                        #ifndef WORLD_CURVATURE
                            float comPos = fract(worldPos.y + cameraPosition.y);
                        #else
                            float comPos = fract(oldPosition.y + cameraPosition.y);
                        #endif
                        comPos = fract(comPos);
                        if (comPos > 0.2 && comPos < 0.99) {
                            emissive = 1.0;
                            albedo.rgb *= LAVA_INTENSITY * 0.9;
                        }
                    }
                    else if (dot(albedo.rgb, albedo.rgb) > 2.7) { // Cauldron Powder Snow
                        metalness = 0.0;
                        smoothness = pow(lAlbedoP, 1.8037) * 0.185;
                        smoothness = min(smoothness, 1.0);
                    }
                    #endif
                }
                else if (material == 162.0) { // Glowstone, Magma Block
					#include "/lib/other/mipLevel.glsl"

                    emissive = pow(lAlbedoP, specB) * fract(specB) * 20.0;

                    emissive += miplevel * 2.5;
                }
            } else {
                if (material == 164.0) { // Chorus Plant, Chorus Flower Age 5
                    if (albedo.g > 0.55 && albedo.r < albedo.g * 1.1) {
                        emissive = 1.0;
                    }
                }
                else if (material == 168.0) { // Overworld Ore Handling Except Redstone
                    float stoneDif = max(abs(albedo.r - albedo.g), max(abs(albedo.r - albedo.b), abs(albedo.g - albedo.b)));
                    float brightFactor = max(lAlbedoP - 1.5, 0.0);
                    float ore = max(max(stoneDif - 0.175 + specG, 0.0), brightFactor);
                    #ifdef EMISSIVE_ORES
					float floorR = floor(albedo.r * 255);
					float floorG = floor(albedo.g * 255);
					float floorB = floor(albedo.b * 255);
					float floorDif = max(abs(floorR - floorG), max(abs(floorR - floorB), abs(floorG - floorB)));
					bool rg = abs(floorR - floorG) < 1;
					bool rb = abs(floorR - floorB) < 1;
					bool gb = abs(floorG - floorB) < 1;
					vec3 floorVec = vec3(floorR, floorG, floorB);
					//The Ore Parts
					emissive *= sqrt4(ore) * 0.15 * ORE_EMISSION;
					//Stone Bases
					if (rg) {
						if (rb) { //red = green = blue
							if (floorR < 123) {
								if (floorR > 119) emissive = 0; //DEEPSLATE: approx. (121, 121, 121)
							}
							else if (floorR > 125) {
								if (floorR < 129) emissive = 0; //STONE: approx. (127, 127, 127)
								else if (floorR > 141) {
									if (floorR < 145) emissive = 0; //STONE: approx. (143, 143, 143)
									else if (floorR > 160 && floorR < 164) emissive = 0; //approx. (162, 162, 162)
								}
							}
							else if (floorR > 90) {
								if (floorR < 94) emissive = 0; //STONE: approx. (92, 92, 92)
								else if (floorR < 102) {
									if (floorR > 98) emissive = 0; //DEEPSLATE: approx. (100, 100, 100)
								}
								else if (floorR > 102) {
									if (floorR < 106) emissive = 0; //STONE: approx. (104, 104, 104)
									else if (floorR > 114 && floorR < 118) emissive = 0; //STONE: approx. (116, 116, 116)
								}
							}
							else if (floorR > 79 && floorR < 83) emissive = 0; //DEEPSLATE: approx. (81, 81, 81)
						}
						else { //red = green != blue
							if (floorR > 59) {
								if (floorR < 63) {
									if (floorB > 65 && floorB < 69) emissive = 0; //DEEPSLATE: approx. (61, 61, 67)
								}
							}
							else if (floorR > 45) {
								if (floorR < 49) {
									if (floorB > 53 && floorB < 57) emissive = 0; //DEEPSLATE: approx (47, 47, 55)
								}
							}
						}
					}
					else if (gb) { //red != green = blue
						if (floorG < 35) {
							if (floorG > 25) {
								if (floorG < 28) {
									if (floorR > 78 && floorR < 82) emissive = 0; //NETHERRACK: approx. (80, 27, 27)
								}
								else if (floorG > 31) {
									if (floorR > 85 && floorR < 89) emissive = 0; //NETHERRACK: approx. (87, 33, 33)
								}
							}
							else if (floorG > 19 && floorG < 24) {
								if (floorR > 79 && floorR < 83 && floorG < 23) emissive = 0; //NETHERRACK: approx. (81, 21, 21)
								else if (floorR > 63 && floorR < 67) emissive = 0; //NETHERRACK: approx. (65, 22, 22)
							}
						}
						else if (floorG > 38) {
							if (floorG < 42) {
								if (floorR > 99 && floorR < 103) emissive = 0; //NETHERRACK: approx. (101, 40, 40)
							}
							else if (floorG > 48) {
								if (floorG < 52) {
									if (floorR > 112 && floorR < 116) emissive = 0; //NETHERRACK: approx. (114, 50, 50)
								}
								else if (floorG < 68) {
									if (floorG > 64) {
										if (floorR > 131 && floorR < 135) emissive = 0; //NETHERRACK: approx. (133, 66, 66)
									}
								}
                                else if (floorG < 94) {
                                    if (floorG > 90 && floorR > 103 && floorR < 107) emissive = 0; //STONE REDSTONE ORE: approx. (105, 92, 92)
                                }
                                else if (floorG > 138) {
                                    if (floorG < 142 && floorR > 145 && floorR < 149) emissive = 0; //STONE REDSTONE ORE: approx. (147, 140, 140)
                                }
							}
						}
					}
					else { //red != green != blue
						if (floorB < 150) {
							if (floorB > 146) {
								if (floorR > 211 && floorR < 215 && floorG > 216 && floorG < 220) emissive = 0; //END_STONE: approx. (213, 218, 148)
							}
							else if (floorB > 137 && floorB < 141) {
								if (floorR > 203) {
									if (floorR < 207 && floorG > 196 && floorG < 200) emissive = 0; //END_STONE: approx. (205, 198, 139)
								}
								else if (floorR > 195 && floorR < 199 && floorG > 188 && floorG < 192) emissive = 0; //END_STONE: approx. (197, 190, 139)
							}
						}
						else if (floorR > 220) {
							if (floorR < 224) {
								if (floorG > 228 && floorG < 232) {
									if (floorB > 162 && floorB < 166) emissive = 0; //END_STONE: approx. (222, 230, 164)
								}
							}
							else if (floorR > 236) {
								if (floorR < 240) {
									if (floorG > 244 && floorG < 248) {
										if (floorB > 178 && floorB < 182) emissive = 0; //END_STONE: approx. (238, 246, 180)
									}
								}
								else if (floorR > 244 && floorR < 248) {
									if (floorG > 248 && floorG < 252) {
										if (floorB > 187 && floorB < 191) emissive = 0; //END_STONE: approx. (246, 250, 189)
									}
								}
							}
						}
					}
                    #endif
                    metalness = 0.0;
                    
                    #if !defined EMISSIVE_ORES || !defined EMISSIVE_IRON_ORE
                        if (abs(specG - 0.07) < 0.0001) {
                            float oreM = min(pow2(ore * ore) * 300.0, 1.0);
                            smoothness = mix(smoothness, 1.0, oreM);
                            metalness = mix(metalness, 0.8, sqrt3(oreM));
                        }
                    #endif
                    #if !defined EMISSIVE_ORES || !defined EMISSIVE_COPPER_ORE
                        if (abs(specG - 0.1) < 0.0001) {
                            float oreM = sqrt3(min(ore * 0.25, 1.0));
                            smoothness = mix(smoothness, 0.5, oreM);
                            if (oreM > 0.01) metalness = 0.8;
                        }
                    #endif
                    #if !defined EMISSIVE_ORES || !defined EMISSIVE_GOLD_ORE
                        if (abs(specG - 0.002) < 0.0001) {
                            float oreM = min(pow2(ore * ore) * 40.0, 1.0);
                            smoothness = mix(smoothness, 0.5, oreM);
                            if (oreM > 0.01) metalness = 0.8;
                        }
                    #endif
                    #if !defined EMISSIVE_ORES || !defined EMISSIVE_EMERALD_ORE
                        if (abs(specG - 0.0015) < 0.0001) {
                            if (ore > 0.01) {
                                float oreM = 1.0 - min(ore * 0.75, 1.0);
                                smoothness = mix(smoothness, 1.0, oreM);
                                extraSpecularM = 1.0;
                            }
                        }
                    #endif
                    #if !defined EMISSIVE_ORES || !defined EMISSIVE_DIAMOND_ORE
                        if (abs(specG - 0.001) < 0.0001) {
                            if (ore > 0.01) {
                                float oreM = 1.0 - min(ore, 1.0);
                                smoothness = mix(smoothness, 1.0, oreM);
                                extraSpecularM = 1.0;
                            }
                        }
                    #endif
                }
                else if (material == 170.0) { // Block of Amethyst++
                    smoothness = min(pow((max(1.73 - lAlbedoP, 0.0) + 1.0), 0.81) * 0.5, 1.0);

                    #ifdef EMISSIVE_AMETHYST_BUDS
                        #ifndef WORLD_CURVATURE
                            vec3 comPos = fract(worldPos.xyz + cameraPosition.xyz);
                        #else
                            vec3 comPos = fract(oldPosition.xyz + cameraPosition.xyz);
                        #endif
                        comPos = abs(comPos - vec3(0.5));
                        float comPosM = max(max(comPos.x, comPos.y), comPos.z);
                        emissive = clamp(1.0 - comPosM * 2.0, 0.0, 1.0);
                        if (emissive > 0.001) {
                            float orangeFactor = sqrt1(emissive * sqrt2(1.0 - lmCoord.x));
                            emissive *= emissive;
                            emissive *= emissive;
                            emissive *= pow(lAlbedoP * 0.7, 4.0) * 0.75;
                            emissive = min(emissive, 0.15) * 1.1;
                            albedo.rgb = pow(albedo.rgb, mix(vec3(1.0), vec3(1.0, 1.0, 1.0), orangeFactor));
                            float whiteFactor = pow(clamp(albedo.g * (1.0 + emissive), 0.0, 1.0), 10.0);
                            albedo.rgb = mix(albedo.rgb, vec3(1.0), whiteFactor);
                        } else emissive = 0.0;
                    #endif
                }
            }
        } else {
            if (mat < 176.5) {
                if (material == 172.0) { // Wet Farmland
                    if (lAlbedoP > 0.3) smoothness = lAlbedoP * 0.7;
                    else smoothness = lAlbedoP * 2.7;
                    smoothness = min(smoothness, 1.0);
                }
                else if (material == 174.0) { // Emissive Redstone Ores
                    float stoneDif = max(abs(albedo.r - albedo.g), max(abs(albedo.r - albedo.b), abs(albedo.g - albedo.b)));
                    float brightFactor = max(lAlbedoP - 1.5, 0.0);
                    float ore = max(max(stoneDif - 0.175 + specG, 0.0), brightFactor);
                    emissive *= sqrt4(ore) * 0.11 * ORE_EMISSION;
                    metalness = 0.0;
                    
                    // Fix white pixels
                    if (emissive > 0.01) {
                        float whitePixelFactor = max(lAlbedoP * lAlbedoP * 2.2, 1.0);
                        albedo.rgb = pow(albedo.rgb, vec3(whitePixelFactor));
                    }
                }
                else if (material == 176.0) { // Beacon
                    #ifndef WORLD_CURVATURE
                        vec3 comPos = fract(worldPos.xyz + cameraPosition.xyz);
                    #else
                        vec3 comPos = fract(oldPosition.xyz + cameraPosition.xyz);
                    #endif
                    comPos = abs(comPos - vec3(0.5));
                    float comPosM = max(max(comPos.x, comPos.y), comPos.z);
                    if (comPosM < 0.4 && albedo.b > 0.5) { // Beacon Core
                        albedo.rgb = vec3(0.35, 1.0, 0.975);
                        if (lAlbedoP > 1.5) albedo.rgb = vec3(1.0);
                        else if (lAlbedoP > 1.3) albedo.rgb = vec3(0.35, 1.0, 0.975);
                        else if (lAlbedoP > 1.15) albedo.rgb *= 0.86;
                        else albedo.rgb *= 0.78;
                        emissive = 1.5;
                    }
                }
            } else {
                if (material == 180.0) { // End Rod
                    if (lAlbedoP > 1.3) {
                        smoothness = 0.0;
                        emissive = 0.4;
                    }
                }
                else if (material == 182.0) { // Froglight-
                    emissive = 0.02 + 0.7 * pow2(pow2(pow2(1.0 - length(vTexCoord.xy - 0.5))));
                    
                    albedo.rgb = pow(albedo.rgb, vec3(1.0 + pow(0.35 * dot(albedo.rgb, vec3(1.0)), 32.0)));
                }
                else if (material == 184.0) { // Rails
                    if (albedo.r > albedo.g * 2.0 + albedo.b) {
                        if (lAlbedoP > 0.45) { // Rail Redstone Lit
                            emissive = lAlbedoP;
                        } else { // Rail Redstone Unlit
                            smoothness = 0.4;
                            metalness = 1.0;
                        }
                    } else {
                        if (albedo.r > albedo.g + albedo.b || abs(albedo.r - albedo.b) < 0.1) { // Rail Gold, Rail Iron
                            smoothness = 0.4;
                            metalness = 1.0;
                        }
                    }
                }
            }
        }
    }
}

#ifdef EMISSIVE_NETHER_ORES
    if (specB < -9.0) {
        emissive = float(albedo.r + albedo.g > albedo.b * 2.0 && albedo.g > albedo.b * (1.2 - albedo.g * 0.5));
        if (abs(albedo.g - albedo.b) < 0.1) emissive *= float(albedo.b > 0.35 || albedo.b < 0.05); // Eliminate Some Pixels On Quartz Ore
        emissive *= albedo.r * 0.05 * ORE_EMISSION;
        if (emissive > 0.01) // Desaturate Some Red-Looking Pixels
        albedo.rgb = mix(albedo.rgb, vec3(dot(albedo.rgb, vec3(0.4, 0.5, 0.07))), clamp((albedo.r - albedo.g) * 2.0, 0.0, 0.3));
    }
#endif