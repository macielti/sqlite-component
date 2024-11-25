(ns sqlite-component.interceptors
  (:require
   [io.pedestal.interceptor :as pedestal.interceptor]
   [next.jdbc :as jdbc]
   [schema.core :as s]))

(s/defn http-friendly-exception
  "https://www.baeldung.com/rest-api-error-handling-best-practices"
  [status-code :- s/Int
   error :- s/Str
   message :- s/Str
   detail :- s/Any]
  (throw (ex-info (format "%s - %s" status-code error)
                  {:status  status-code
                   :error   error
                   :message message
                   :detail  detail})))

(s/defn resource-existence-check-interceptor
  "resource-identifier-fn -> function used to extract param used to query the resource, must receive a context as argument.
  sql-query -> postgresql query that will try to find the resource using the resource identifier"
  [resource-identifier-fn
   sql-query]
  (pedestal.interceptor/interceptor
   {:name  ::resource-existence-check-interceptor
    :enter (fn [{{:keys [components]} :request :as context}]
             (let [database (:sqlite components)
                   resource-identifier (resource-identifier-fn context)
                   resource (with-open [conn (jdbc/get-connection database)]
                              (some-> (jdbc/execute! conn [sql-query resource-identifier]) first))]
               (when-not resource
                 (http-friendly-exception 404
                                          "resource-not-found"
                                          "Resource could not be found"
                                          "Not Found")))
             context)}))
