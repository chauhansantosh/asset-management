import com.hcl.UserPasswordEncoderListener
import com.hcl.MyUserDetailsService
// Place your Spring DSL code here
beans = {
    userPasswordEncoderListener(UserPasswordEncoderListener)
	userDetailsService(MyUserDetailsService)
}
