Vue.component("edit-profile", {
    data: function () {
        return {
            user: {id : "", name : "", surname: "" , username: "", password: "", birthDate : "", role : "" },
            birth : {},
            configHeaders: {
                headers: {
                    token: $cookies.get("token"),
                }
            }
        }
    },
    template: ` 
<div>
    <h1>Registracija</h1>
    <div class="login-container">
    <div class="login-box">
    <table class="registration-table">
	    <tr><td>Ime: </td>
	    <td class="td-content"><input type="text" v-model = "user.name" name="name" v-on:change="validateName"><div id="spanName" style="visibility: hidden">Morate unijeti ime</div></td> </tr>
	    <tr><td>Prezime: </td>
	    <td class="td-content"><input type="text" v-model = "user.surname" name="surname" v-on:change="validateSurname"> <div id="spanSurname" style="visibility: hidden">Morate unijeti prezime</div> </td></tr>
	    <tr><td>Username: </td>
	    <td class="td-content"><input type="text" v-model = "user.username" name="username" v-on:change="validateUsername"> <div id="spanUsername" style="visibility: hidden">Username mora imati minimalno 5 karaktera</div>  </td></tr>
	    <tr><td>Password: </td>
	    <td class="td-content"><input type="text" v-model = "user.password" name="password" v-on:change="validatePassword"><div id="spanPassword" style="visibility: hidden">Password mora imati minimalno 5 karaktera</div> </td></tr>
	    
	    <button class="login-button" v-on:click = "edit">Edit</button>
    </table>
    </div>
    </div>
</div>		  
`
    ,
    methods : {
        edit : function () {
            if(this.user.name.length === 0 || this.user.surname.length === 0 || this.user.username.length < 5 || this.user.password.length < 5){
                alert("Neispravan unos!!!")
                return;
            }
            const promise = axios.post('/users/edit-user', this.user, this.configHeaders);
            promise.then(response => {
                if(response.data === "") {
                    alert("Vec postoji sa tim username-om");
                } else {
                    $cookies.remove('token');
                    $cookies.set('token', response.data, 10000)
                    router.push(`/`)
                }
            })
        },
        validateName : function () {
            if(this.user.name.length  < 2) {
                document.getElementById("spanName").style.visibility = "visible"
                document.getElementById("spanName").style.color = "red"
            } else {
                document.getElementById("spanName").style.visibility = "hidden"
            }
        },
        validateSurname : function () {
            if(this.user.surname.length < 2) {
                document.getElementById("spanSurname").style.visibility = "visible"
                document.getElementById("spanSurname").style.color = "red"
            } else {
                document.getElementById("spanSurname").style.visibility = "hidden"
            }
        },
        validatePassword : function () {
            if(this.user.password.length < 5) {
                document.getElementById("spanPassword").style.visibility = "visible"
                document.getElementById("spanPassword").style.color = "red"
            } else {
                document.getElementById("spanPassword").style.visibility = "hidden"
            }
        },
        validateUsername : function () {
            if(this.user.username.length < 5) {
                document.getElementById("spanUsername").style.visibility = "visible"
                document.getElementById("spanUsername").style.color = "red"
            } else {
                document.getElementById("spanUsername").style.visibility = "hidden"
            }
        }


    },

    mounted () {
        axios
            .get('/users/get-user', this.configHeaders)
            .then(response => {
                this.user = response.data;
            })
    },
});