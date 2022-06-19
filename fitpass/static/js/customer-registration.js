Vue.component("customer-registration", {
    data: function () {
        return {
            customer: { name : "", surname: "" , username: "", password: "", birthDate : {}},
            birth : {}
        }
    },
    template: ` 
<div>
    <h1>Registracija</h1>
    <div class="login-container">
    <div class="login-box">
    <table class="registration-table">
	    <tr><td>Ime: </td>
	    <td class="td-content"><input type="text" v-model = "customer.name" name="name" v-on:change="validateName"><div id="spanName" style="visibility: hidden">Morate unijeti ime</div></td> </tr>
	    <tr><td>Prezime: </td>
	    <td class="td-content"><input type="text" v-model = "customer.surname" name="surname" v-on:change="validateSurname"> <div id="spanSurname" style="visibility: hidden">Morate unijeti prezime</div> </td></tr>
	    <tr><td>Username: </td>
	    <td class="td-content"><input type="text" v-model = "customer.username" name="username" v-on:change="validateUsername"> <div id="spanUsername" style="visibility: hidden">Username mora imati minimalno 5 karaktera</div>  </td></tr>
	    <tr><td>Password: </td>
	    <td class="td-content"><input type="password" v-model = "customer.password" name="password" v-on:change="validatePassword"><div id="spanPassword" style="visibility: hidden">Password mora imati minimalno 5 karaktera</div> </td></tr>
	    <tr><td>Pol: </td>
	    <td><label>Musko</label><input type="radio" v-model = "customer.gender" name="gender" value="true" > <br>
	    <label>Zensko</label><input type="radio" v-model = "customer.gender" name="gender" value="false" > </td>
	    </tr>
	    <tr>
	    <td>Datum rodjenja: </td>
	    <td><input type="date" v-model = "birth.b" name="birthdate"> </td></tr>
	    <button class="login-button" v-on:click = "register">Registruj se</button>
    </table>
    </div>
    </div>
</div>		  
`
    ,
    methods : {
        register : function () {
            if(this.customer.name.length === 0 || this.customer.surname.length === 0 || this.customer.username.length < 5 || this.customer.password.length < 5){
                alert("Neispravan unos!!!")
                return;
            }
            const date = new Date(this.birth.b);
            const month = date.getMonth()+1;
            let text = '{ "year" :' + date.getFullYear() + ', "month" : ' + month + ', "day" : ' + date.getDate() + ' }';
            const obj = JSON.parse(text);
            this.customer.birthDate = obj;
            const promise = axios.post('/users/register-customer', this.customer);
            promise.then(response => {
                if(response.data === false) {
                    alert("Vec postoji sa tim username-om");
                } else {
                    router.push(`/login`)
                }
            })
        },
        validateName : function () {
            if(this.customer.name.length  < 2) {
                document.getElementById("spanName").style.visibility = "visible"
                document.getElementById("spanName").style.color = "red"
            } else {
                document.getElementById("spanName").style.visibility = "hidden"
            }
        },
        validateSurname : function () {
            if(this.customer.surname.length < 2) {
                document.getElementById("spanSurname").style.visibility = "visible"
                document.getElementById("spanSurname").style.color = "red"
            } else {
                document.getElementById("spanSurname").style.visibility = "hidden"
            }
        },
        validatePassword : function () {
            if(this.customer.password.length < 5) {
                document.getElementById("spanPassword").style.visibility = "visible"
                document.getElementById("spanPassword").style.color = "red"
            } else {
                document.getElementById("spanPassword").style.visibility = "hidden"
            }
        },
        validateUsername : function () {
            if(this.customer.username.length < 5) {
                document.getElementById("spanUsername").style.visibility = "visible"
                document.getElementById("spanUsername").style.color = "red"
            } else {
                document.getElementById("spanUsername").style.visibility = "hidden"
            }
        }


    },

    mounted () {
    },
});