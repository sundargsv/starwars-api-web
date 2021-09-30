package com.sundar.starwars.caching;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StarWarsCacheKey {

    private String type;
    private String name;
}
