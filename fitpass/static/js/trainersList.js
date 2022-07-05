Vue.component("trainers-list", {
    data: function () {

        return {
            usersDto: "",
            userInfo: {
                username: "",
                role: ""
            },
            configHeaders: {
                headers: {
                    token: $cookies.get("token"),
                }
            },
            form: {
                searchInput: "",
                userRole: "",
                userType: "",
            },
            sortDirection: null,
            sortIndex: null,
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
		<td>
		    {{u.gender}}
		</td>
		<td>
		    {{u.birth}}
        </td>
	</tr>
</table>
	<button class="login-button" v-on:click="logout">Odjavi se</button>
</div>		  
`
    ,
    methods:
        {
            logout: function () {
                $cookies.remove('token')
                router.push('/login')
            }
        },

    mounted() {
        if ($cookies.get("token") == null) {
            router.push("/login")
        }
        axios.post('users/get-info', $cookies.get("token"), this.configHeaders)
            .then(response => {
                this.userInfo = response.data
                if (this.userInfo.role !== 'MANAGER') {
                    router.push("/")
                }
            })
        axios
            .get('/manager/get-facility/trainers', this.configHeaders)
            .then(response => {
                this.usersDto = response.data
            })


    },
});