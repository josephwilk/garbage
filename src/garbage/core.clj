(ns garbage.core
  "Assumes timing is perfect. Which it never is..."
  (:require [overtone.osc :as osc]))

(def osc-midi-in-port 4562)
(def erlang-port 4560)

(defonce midi-client         (osc/osc-client "localhost" erlang-port))
(def default-port    :iac_bus_1)
(def default-channel 1)

(defn ts
  ([]     (System/currentTimeMillis))
  ([dur]  (+ (* 1000 dur) (ts))))

(defn midi-on-osc [channel note vel ts]
  (osc/osc-send-bundle
   midi-client
   (osc/osc-bundle
    ts
    (osc/osc-msg "/send_after" "localhost" (int 4561) "/iac_bus_1/note_on" (int channel) (int note) (int vel)))))

(defn midi-off-osc [channel note ts]
  (osc/osc-send-bundle
   midi-client
   (osc/osc-bundle
    ts
    (osc/osc-msg "/send_after" "localhost" (int 4561) "/iac_bus_1/note_off" (int channel) (int note) (int 127)))))

(defn midi-on  [n vel opts]
  (let [port    (get opts :port default-port)
        channel (get opts :channel default-channel)
        ts (get opts :ts (ts))]
    (midi-on-osc channel n vel ts)))

(defn midi-off [n opts]
  (let [port :iac_bus_1
        ts (get opts :ts (ts))]
    (midi-off-osc (get opts :channel 1) n ts)))

(defn midi
  ([n]      (midi n 127 {:sustain 1}))
  ([n opts] (midi n 127 opts))
  ([n vel opts]
   (let [sustain (get opts :sustain 1)]
     (midi-on n vel opts)
     (midi-off n (merge opts {:ts (ts sustain)})))))

(comment
  (midi-on 54 127 {:channel 1})
  (midi-off 54 {:channel 6})

  (midi 54 127 {:channel 6 :sustain 2})

  (midi 54 {:channel 4 :sustain 2})
  )