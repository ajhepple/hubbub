class UrlMappings {

        /* NB These mappings are bi-directional. That is, they not only
         * map paths to controllers but they also define the paths resulting
         * from redirects to specified controller actions
         *
         * Rules are applied in the order that they appear. Place rules in
         * order of decreasing specificity.
         */

	static mappings = {
            "/$controller/$action?/$id?(.$format)?"{
                constraints {
                    // apply constraints here
                }
            }

            // the timeline of the logged in user
            "/timeline" controller: "post", action: "personal"

            name userFeed: "/users/$loginId/feed/$format?" {
                controller = "post"
                action = "feed"
                constraints {
                    format(inList: ['rss', 'atom'])
                }
            }

            "/users/$id"(controller: "post", action: "timeline")

            "/"(view:"/index")
            "500"(view:'/error')
	}
}
