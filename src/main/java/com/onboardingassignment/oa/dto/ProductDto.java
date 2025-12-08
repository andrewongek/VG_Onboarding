package com.onboardingassignment.oa.dto;

import com.onboardingassignment.oa.model.Language;

public record ProductDto (int id, String code, String name, Language language, int stock, int price){
}
