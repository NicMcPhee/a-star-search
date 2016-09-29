(ns search.algorithms
  (:require clojure.set
            [clojure.data.priority-map :as pm])
  (:gen-class))

(defn breadth-first-search [children-fn max-states start-state goal-state]
  (loop [max-states max-states
         frontier (conj (clojure.lang.PersistentQueue/EMPTY) start-state)
         visited #{}
         came-from {}]
    (if (or (neg? max-states)
            (empty? frontier)
            (= (peek frontier) goal-state))
      came-from
      (let [current (peek frontier)
            children (set (children-fn current))
            unvisited-children (clojure.set/difference children visited)
            new-frontier (reduce conj (pop frontier) unvisited-children)
            new-visited (clojure.set/union children visited)
            new-came-from (reduce #(assoc %1 %2 current) came-from unvisited-children)]
        (recur (- max-states (count unvisited-children))
               new-frontier
               new-visited
               new-came-from)))))

(defn shortest-path [children-fn cost-fn max-states start-state goal-state]
  (loop [max-states max-states
         frontier (pm/priority-map start-state 0)
         came-from {}
         cost-so-far {start-state 0}]
    (if (or (neg? max-states)
            (empty? frontier)
            (= (first (peek frontier)) goal-state))
      [came-from cost-so-far]
      (let [current (first (peek frontier))
            current-cost (cost-so-far current)
            children (set (children-fn current))
            children-costs (reduce #(assoc %1 %2 (+ current-cost (cost-fn current %2))) {} children)
            children-to-add (filter #(or (not (contains? cost-so-far %))
                                         (< (children-costs %) (cost-so-far %))) children)
            new-cost-so-far (reduce #(assoc %1 %2 (children-costs %2)) cost-so-far children-to-add)
            new-frontier (reduce #(assoc %1 %2 (children-costs %2)) (pop frontier) children-to-add)
            new-came-from (reduce #(assoc %1 %2 current) came-from children-to-add)]
        (recur (- max-states (count children-to-add))
               new-frontier
               new-came-from
               new-cost-so-far)))))

(defn heuristic-search [children-fn heuristic-fn start-state goal-state & {:keys [max-states] :or {max-states 1000000}}]
  (loop [frontier (pm/priority-map start-state 0)
         came-from {}
         visited #{}]
    (if (or (empty? frontier)
            (>= (count came-from) max-states)
            (= (first (peek frontier)) goal-state))
      came-from
      (let [current (first (peek frontier))
            children (set (children-fn current))
            unvisited-children (clojure.set/difference children visited)
            heuristics (map (partial heuristic-fn goal-state) unvisited-children)
            new-frontier (reduce #(assoc %1 %2 (heuristic-fn goal-state %2)) (pop frontier) unvisited-children)
            new-came-from (reduce #(assoc %1 %2 current) came-from unvisited-children)
            new-visited (clojure.set/union children visited)]
        (recur new-frontier new-came-from new-visited)))))

(defn extract-path [came-from start-state goal-state]
  (loop [current-state goal-state
         path []]
    (cond
      (nil? current-state) nil
      (= current-state start-state) (reverse (conj path start-state))
      :else (recur (came-from current-state)
                   (conj path current-state)))))

(defn a-star[children-fn heuristic-fn max-states start-state goal-state]

  (loop [max-states max-states ;;Generational loop
         frontier (pm/priority-map start-state 0)
         came-from {start-state nil}
         cost-so-far {start-state 0}]
    (if (or (empty? frontier)
            (<= max-states 0)
            (= (first (peek frontier)) goal-state))
      came-from
      (let [current (first (peek frontier))
            current-cost (cost-so-far current)
            current-children (children-fn current)
            child-cost (+ current-cost 1) ;; increasing cost by one because all costs are the same for sudoku
            new-vals-map (loop [new-cost-so-far cost-so-far ;;Intergenerational loop.
                                new-came-from came-from
                                child-list (rest current-children)
                                child (first current-children)
                                new-frontier frontier]
                               (if (nil? child)
                                    ;;If we've run out of children to mangle
                                    ;;So return the values we need.
                                 {:frontier (dissoc new-frontier current)
                                  :came-from new-came-from
                                  :cost-so-far new-cost-so-far}
                                    ;;if we don't have that childs cost yet
                                    ;; or the childs current cost is lower than previously recorded
                                 (if (or (not (contains? new-cost-so-far child))
                                         (< child-cost (get new-cost-so-far child)))
                                      ;;Recur in the case we need to do things.
                                   (recur
                                     (conj new-cost-so-far [child child-cost])
                                     (conj new-came-from   [child current])
                                     (rest child-list)
                                     (first child-list)
                                     (assoc new-frontier child (+ child-cost (heuristic-fn current child))))
                                      ;;recur in the case we don't
                                   (recur new-cost-so-far new-came-from
                                          (rest child-list)
                                          (first child-list)
                                          frontier))))]
        ;;Generational Recur
        (recur (- max-states (count current-children))
               (:frontier new-vals-map)
               (:came-from new-vals-map)
               (:cost-so-far new-vals-map))))))
