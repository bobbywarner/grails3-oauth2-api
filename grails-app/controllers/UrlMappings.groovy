class UrlMappings {

    static mappings = {

        '/register'(controller: 'register') {
            action = [POST: 'save']
            format = 'json'
        }

        '/profile'(controller: 'profile') {
            action = [GET: 'index']
        }

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
