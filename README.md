# Garbage

Speak & schedule midi with Clojure through OsMidi && stealing SonicPis Erlang midi scheduler.

```
==^==
|[[[|
|[[[|
'---'
```

#### Why?

* Java's midi is terrible, use c++ and JUCE instead.
* Erlang is fab at schedulling and the scheduler is written by Joe Armstrong (one of the creators of Erlang).
* Clojure makes me happy.

#### Why not?

* The more sensible thing would be to just use SonicPi. 
* I'm not sure I have the will power to write a virtual scheduler to do timing properly just so I can write Clojure instead of Ruby.

## Install

* `brew install erlang`

* `git clone git@github.com:llloret/osmid.git` (OSMid https://github.com/llloret/osmid)

* `git clone git@github.com:samaaron/sonic-pi.git` (SonicPi's Erlang server - https://github.com/samaaron/sonic-pi)

## Running

```shell
#Have to use absolute paths
/usr/josephwilk/stuff/osmid/o2m -b -i 4561 -O 4562 -m 6
erl -pz "/usr/josephwilk/stuff/sonic-pi/app/server/erlang" -s pi_server start 4560
```
Via a repl:

```clojure
(midi 52 {:port 1, :channel :iac_bus, sustain: 2})
```
