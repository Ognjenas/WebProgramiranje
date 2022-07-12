Vue.component("comment-panel", {
    data: function () {
        return {
            userInfo : {
                username : "",
                role : ""
            },
            configHeaders : {
                headers: {
                    token: $cookies.get("token"),
                }
            },
            submittedComments:"",
            finishedComments:"",

        }
    },
    template: ` 
<div>
<div class="facility-list-container" >
<p>SUBMITTED COMMENTS WAITING CHECK</p>

<table class="show-facilities-table">
    <tr>
    <th>Id</th>
    <th>Username</th>
    <th>Facility Name</th>
    <th>Text</th>
    <th>Status</th>
    <th>Grade</th>
    </tr>
    
    <tr v-for="com in submittedComments">
    <td>{{com.id}}</td>
    <td>{{com.customerUsername}}</td>
    <td>{{com.facilityName}}</td>
    <td>{{com.text}}</td>
    <td>{{com.status}}</td>
    <td>{{com.grade}}</td>
    <td><button class="login-button"  v-on:click="confirm(com.id)">APPROVE</button></td>
    <td><button class="login-button"  v-on:click="reject(com.id)">REJECT</button></td>
</tr>
</table>
<p>CHECKED COMMENTS</p>
<table class="show-facilities-table">
<tr>
    <th>Id</th>
    <th>Username</th>
    <th>Facility Name</th>
    <th>Text</th>
    <th>Status</th>
    <th>Grade</th>
    </tr>
    
    <tr v-for="com in finishedComments">
    <td>{{com.id}}</td>
    <td>{{com.customerUsername}}</td>
    <td>{{com.facilityName}}</td>
    <td>{{com.text}}</td>
    <td>{{com.status}}</td>
    <td>{{com.grade}}</td>
    </tr>
</table>
</div>
</div>		  
`
    ,
    methods : {
        confirm(id){
            axios.post('/administrator/confirm-comment',id,this.configHeaders)
                .then(response=>{
                    window.location.reload();
                });

        },
        reject(id){
            axios.post('/administrator/reject-comment',id,this.configHeaders)
                .then(response=>{
                    window.location.reload();
                });
        },

    },

    mounted () {
        if($cookies.get("token") == null) {
            router.push("/login")
        }
        axios.post('users/get-info', $cookies.get("token"),this.configHeaders)
            .then(response => {
                this.userInfo = response.data
                if(this.userInfo.role !== 'ADMINISTRATOR') {
                    router.push("/")
                }
            })
        axios.get('/administrator/get-unapproved-comments',this.configHeaders)
            .then(response=>{
                this.submittedComments=response.data.allComments;
            })
        axios.get('/administrator/get-finished-comments',this.configHeaders)
            .then(response=>{
                this.finishedComments=response.data.allComments;
            })
    },
});