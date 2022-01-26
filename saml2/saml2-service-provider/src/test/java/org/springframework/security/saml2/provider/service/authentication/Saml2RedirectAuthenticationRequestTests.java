/*
 * Copyright 2002-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.security.saml2.provider.service.authentication;

import org.junit.jupiter.api.Test;

import org.springframework.util.SerializationUtils;

import static org.assertj.core.api.Assertions.assertThat;

class Saml2RedirectAuthenticationRequestTests {

	private static final String IDP_SSO_URL = "https://sso-url.example.com/IDP/SSO";

	@Test
	void serializeWhenDeserializeThenSameFields() {
		Saml2RedirectAuthenticationRequest authenticationRequest = getAuthenticationRequestBuilder().build();
		byte[] bytes = SerializationUtils.serialize(authenticationRequest);
		Saml2RedirectAuthenticationRequest deserializedAuthenticationRequest = (Saml2RedirectAuthenticationRequest) SerializationUtils
				.deserialize(bytes);
		assertThat(deserializedAuthenticationRequest).usingRecursiveComparison().isEqualTo(authenticationRequest);
	}

	@Test
	void serializeWhenDeserializeAndCompareToOtherThenNotSame() {
		Saml2RedirectAuthenticationRequest authenticationRequest = getAuthenticationRequestBuilder().build();
		Saml2RedirectAuthenticationRequest otherAuthenticationRequest = getAuthenticationRequestBuilder()
				.relayState("relay").build();
		byte[] bytes = SerializationUtils.serialize(otherAuthenticationRequest);
		Saml2RedirectAuthenticationRequest deserializedAuthenticationRequest = (Saml2RedirectAuthenticationRequest) SerializationUtils
				.deserialize(bytes);
		assertThat(deserializedAuthenticationRequest).usingRecursiveComparison().isNotEqualTo(authenticationRequest);
	}

	private Saml2RedirectAuthenticationRequest.Builder getAuthenticationRequestBuilder() {
		return Saml2RedirectAuthenticationRequest
				.withAuthenticationRequestContext(
						TestSaml2AuthenticationRequestContexts.authenticationRequestContext().build())
				.samlRequest("request").authenticationRequestUri(IDP_SSO_URL);
	}

}