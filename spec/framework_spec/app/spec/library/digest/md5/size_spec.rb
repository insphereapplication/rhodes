require File.dirname(File.join(__rhoGetCurrentDir(), __FILE__)) + '/../../../spec_helper'
require File.dirname(File.join(__rhoGetCurrentDir(), __FILE__)) + '/shared/constants'
require File.dirname(File.join(__rhoGetCurrentDir(), __FILE__)) + '/shared/length'

describe "Digest::MD5#size" do
  it_behaves_like :md5_length, :size
end

