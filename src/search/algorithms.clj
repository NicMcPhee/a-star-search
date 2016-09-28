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


(defn helper_1 [cost-thus-far children new-costs]
	;;(println new-costs)
	(for [child children cost new-costs]
		(if (< cost (cost-thus-far child))
			child
		)
	)
	;(map #(if(< %2 (%1 cost-thus-far)) %1) children new-costs)
)

;;
;; Another try at that a-star implementation
;;
(defn a-star [children-fn heuristic-fn start-state goal-state cost-fn]
	(loop [frontier (pm/priority-map start-state 0)
		cost-so-far {start-state 0}
		came-from {}]

		;; Check if we're done!
		(if (or (empty? frontier)
			(= (first (peek frontier)) goal-state))
			
			;; return map of states we explored to find the goal
			came-from
			
			;; Otherwise, keep on looking!
			(let [
				current (first (peek frontier))
				current-cost (cost-so-far current)
				children (set (children-fn current))
				;; Calculate heuristic value of each child
                                children-costs (reduce #(assoc %1 %2 (+ current-cost (cost-fn current %2))) {} children)
				;; Generate set of unvisited children
                                children-to-add (filter #(or (not (contains? cost-so-far %))
                                         (< (children-costs %) (cost-so-far %))) children)
				;; Find children whose new-costs are lower than before
				;; Generate a new frontier
                                new-cost-so-far (reduce #(assoc %1 %2 (children-costs %2)) cost-so-far children-to-add)
				new-frontier (reduce #(assoc %1 %2 (+ (heuristic-fn goal-state %2) (children-costs %2))) (pop frontier) children-to-add)
				;; Generate a new came-from!
				new-came-from (reduce #(assoc %1 %2 current) came-from children-to-add)
			     ]
				(recur new-frontier new-cost-so-far new-came-from)
			)
		)
	)
)

