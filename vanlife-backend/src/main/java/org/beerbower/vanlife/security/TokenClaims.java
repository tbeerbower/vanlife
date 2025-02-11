package org.beerbower.vanlife.security;

import java.util.Date;

public record TokenClaims(
        String subject,
        Date expiresAt,
        Date issuedAt,
        String issuer,
        String auth
) {
}
