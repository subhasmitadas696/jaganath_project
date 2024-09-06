package com.csmtech.SJTA.config;

/**
 * @Auth Rashmi Ranjan Jena
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.csmtech.SJTA.filter.JwtFilter;
import com.csmtech.SJTA.serviceImpl.LandAppLoginServiceImpl;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LandAppLoginServiceImpl userDetailsService;

	@Autowired
	private JwtAuthaniticationEntryPoint entry;

	@Autowired
	private JwtFilter jwtFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return new JwtAuthaniticationEntryPoint();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests()
		.antMatchers("/getFormName", "/getallOfficersApi", "/getallApprovalAction", "/fillWorkflow", "/setWorkflow").permitAll()
				.antMatchers("/landAppregistratation/registratationtookan").permitAll()
				.antMatchers("/landAppregistratation/generate").permitAll()
				.antMatchers("/api/sendotp", "/api/setnewpassword", "/api/matchotp", "/api/generatecaptcha").permitAll()
				.antMatchers("/userregister/saveuser", "/userregister/savemobileno", "/userregister/updatemobilenos","/userregister/varifiedotp", "/userregister/generatecaptcha")
				.permitAll().antMatchers("/api/officer/register").permitAll()
				.antMatchers("/mainpagination/registerpage").permitAll()
				.antMatchers("/showcitizenInfo/getcitizeninfo", "/faq/questionsandanswers").permitAll()
				.antMatchers("/mainpermission/getrole", "/mainpermission/getmodulewisemenu","/mainpermission/saveBatchrecord", "/mainpermission/updateBatchrecord")
				.permitAll().antMatchers("/saveFileToTemp").permitAll().antMatchers("/showcitizenInfo/pagination")
				.permitAll()
				.antMatchers("/fillDropDown", "/nocUserrecord/savenocRecxord", "/nocUserrecord/savenocNocRecxord",
						"/nocUserrecord/saveNocFileREcord")
				.permitAll().antMatchers("/saveFileToTemp").permitAll()
				.antMatchers("/showcitizenInfo/search", "/raisequery", "/viewquery").permitAll()
				.antMatchers("/statistics/getlandstatistics").permitAll()
				.antMatchers("/mainView/getlandAppDetails", "/api/notifications/getnotifications",
						"/api/notifications/viewnotifications", "/api/notifications/getonenotification",
						"/api/notifications/add", "/api/notifications/update", "/api/notifications/delete",
						"/api/tender/gettender")
				.permitAll().antMatchers("/api/banner/districtcodes").permitAll()
				.antMatchers("/mainpermission/getrole", "/mainpermission/getmodulewisemenu","/mainpermission/saveBatchrecord", "/mainpermission/updateBatchrecord")
				.permitAll()
				.antMatchers("/ckEditorFileUpload", "/ckEditorImageUpload", "/downloadDocument/**","/viewDocument/**", "/downloadDocumentPost")
				.permitAll().antMatchers("/mainView/getlandAppDetails").permitAll()
				//Ranjan Change
				.antMatchers("/mainRecord/distDropDownData"
						,"/mainRecord/getThasilRecord"
						,"/mainRecord/getVillageRecord"
						,"/mainRecord/getVillagekathaRecordMore",
						"/mainRecord/tahasilLogin","/mainRecord/generate").permitAll()
				.anyRequest().authenticated().and()
				.exceptionHandling(ex -> ex.authenticationEntryPoint(entry))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
