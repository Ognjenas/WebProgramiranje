Vue.component("login", {
    data: function () {
        return {
            login: {},
        }
    },
    template: ` 
<div>
    <h1>Login</h1>
    <div class="login-container">
    <div class="login-box">
    <table>
	    <tr><td>Username: </td>
	    <td><input type="text" v-model = "login.username" name="username"> </td></tr>
	    <tr><td>Password: </td>
	    <td><input type="password" v-model = "login.password" name="password"> </td></tr>
	    <tr><td><button v-on:click = "tryLogin" class="login-button">Login</button></td>
	    <td><button v-on:click = "registration" class="login-button">Registracija</button></td></tr>
	</table>
	</div>
</div>		
</div>  
`
    ,
    methods: {
        tryLogin: function () {
            const promise = axios.post('/login', this.login);
            promise.then(response => {
                if (response.data === "") {
                    alert("Pogresan login");
                } else {
                    $cookies.set("token", response.data, 10000)
                    router.push(`/`)
                }
            })
        },
        registration: function () {
            router.push("/customer-registration")
        }

    },

    mounted() {
    },
});