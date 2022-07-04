Vue.component("users-list", {
    data: function () {

        return {
            usersDto: "",
            userInfo : {
                username : "",
                role : ""
            },
            configHeaders : {
                headers: {
                    token: $cookies.get("token"),
                }
            }
        }
    },
    template: ` 
<div>
    <label>Username: {{userInfo.username}}</label>
	<table>
	<tr>
	    <th></th>
		<th>Name</th>
		<th>Surname</th>
		<th>Username</th>
		<th>Role</th>
		<th>Gender</th>
		<th>Birth</th>
	</tr>
		
	<tr v-for="u in usersDto.users" >
		<td></td>
		<td>{{u.name}}</td>
		<td>{{u.surname}}</td>
		<td>
		    {{u.username}}
		</td>
		<td>{{u.role}}</td>
		<td>
		    {{u.gender}}
		</td>
		<td>
		    {{u.birth}}
        </td>
	</tr>
</table>
	<button class="login-button" v-on:click="logout">Odjavi se</button>
	<button class="login-button" v-on:click="createTrainer">Create trainer</button>
	<button class="login-button" v-on:click="createManager">Creater manager</button>
</div>		  
`
    ,
    methods :
        {
            logout : function () {
                $cookies.remove('token')
                router.push('/login')
            },
            createTrainer : function () {
                router.push('/create-trainer')
            },
            createManager : function () {
                router.push('/create-manager')
            }
        },

    mounted () {
        if($cookies.get("token") == null) {
            router.push("/login")
        }
        axios.post('users/get-info', $cookies.get("token"))
            .then(response => {
                this.userInfo = response.data
                if(this.userInfo.role !== 'ADMINISTRATOR') {
                    router.push("/")
                }
            })
        axios
            .get('/administrator/get-users', this.configHeaders)
            .then(response =>{this.usersDto = response.data
            })


    },
});