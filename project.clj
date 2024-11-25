(defproject net.clojars.macielti/sqlite-component "2.1.0"

  :description "SQLite component for Integrant"

  :url "https://github.com/macielti/sqlite-component"

  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}

  :dependencies [[org.clojure/clojure "1.12.0"]
                 [integrant "0.13.1"]
                 [io.pedestal/pedestal.interceptor "0.7.2"]
                 [org.xerial/sqlite-jdbc "3.41.2.1"]
                 [com.github.seancorfield/next.jdbc "1.3.955"]]

  :profiles {:dev {:resource-paths ^:replace ["test/resources"]

                   :test-paths     ^:replace ["test/unit" "test/integration" "test/helpers"]

                   :plugins        [[com.github.clojure-lsp/lein-clojure-lsp "1.4.15"]
                                    [com.github.liquidz/antq "RELEASE"]
                                    [lein-cloverage "1.2.4"]]

                   :dependencies   [[net.clojars.macielti/common-test-clj "3.1.1"]
                                    [danlentz/clj-uuid "0.2.0"]
                                    [nubank/matcher-combinators "3.9.1"]
                                    [hashp "0.2.2"]]

                   :migratus       {:store         :database
                                    :migration-dir "migrations-sqlite"}

                   :injections     [(require 'hashp.core)]

                   :aliases        {"clean-ns"     ["clojure-lsp" "clean-ns" "--dry"] ;; check if namespaces are clean
                                    "format"       ["clojure-lsp" "format" "--dry"] ;; check if namespaces are formatted
                                    "diagnostics"  ["clojure-lsp" "diagnostics"]
                                    "lint"         ["do" ["clean-ns"] ["format"] ["diagnostics"]]
                                    "clean-ns-fix" ["clojure-lsp" "clean-ns"]
                                    "format-fix"   ["clojure-lsp" "format"]
                                    "lint-fix"     ["do" ["clean-ns-fix"] ["format-fix"]]}}})
