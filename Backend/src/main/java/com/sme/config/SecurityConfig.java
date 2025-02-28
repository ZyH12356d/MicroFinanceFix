//package com.sme.config;
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//    @Autowired
//    private JwtRequestFilter jwtRequestFilter;
//
//    @Autowired
//    private CustomUserDetailsService customUserDetailsService;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeHttpRequests()
//                .requestMatchers("/auth/**").permitAll() // Public routes
//                .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN") // Dynamic role
//                .requestMatchers("/staff/**").hasAuthority("ROLE_STAFF") // Dynamic role
//                .anyRequest().authenticated()
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .userDetailsService(customUserDetailsService); // Use dynamic user details
//
//        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }
//}
